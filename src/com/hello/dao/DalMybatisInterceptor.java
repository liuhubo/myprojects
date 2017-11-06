package com.hello.dao;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * mybatis插件，拦截mybatis中Executor中所有对数据库的操作，检查当前是 查询 还是更新操作。<br>
 * MyBatis的所有操作最后都被转换为Executor的query或者update方法。<br>
 *
 * @author mengchuang
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class DalMybatisInterceptor implements Interceptor {

    private Logger log = LoggerFactory.getLogger(DalMybatisInterceptor.class);

    /**
     * 根据SqlCommandType切换数据源
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = ms.getSqlCommandType();
        if (sqlCommandType == SqlCommandType.SELECT) {
            log.debug("sqlCommandType:{},will switch to read.", sqlCommandType);
            DBContextHolder.switchToRead();

            if (needForceWrite(invocation)) {
                DBContextHolder.switchToWrite();
            }
        } else {
            log.debug("sqlCommandType:{},will switch to write.", sqlCommandType);
            DBContextHolder.switchToWrite();
        }
        return invocation.proceed();
    }

    /**
     * 如果查询的Mapper interface中包含 {@link DALConsts.FORCE_WRITE} 参数，且值为true，则且换为主库查询
     */
    private boolean needForceWrite(Invocation invocation) {
        boolean forceWrite = false;
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }

        if (parameter != null && parameter instanceof ParamMap) {
            ParamMap<Object> params = (ParamMap<Object>) parameter;
            if (params.containsKey(DALConsts.FORCE_WRITE)) {
                Object forceWriteParam = params.get(DALConsts.FORCE_WRITE);
                if (forceWriteParam != null && Boolean.valueOf(forceWriteParam.toString())) {
                    log.debug("force-write,will switch to write.");
                    forceWrite = true;
                }
            }
        }
        return forceWrite;
    }

    /**
     * 只拦截Executor
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
