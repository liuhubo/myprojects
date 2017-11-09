#!/bin/bash
ROOT_DIR="/workspace/carkey"
EASYBIKE_GW_ROOT_DIR=$ROOT_DIR/AppHellobikeBosGW
EASYBIKE_GW_DEFAULT_DIR=$EASYBIKE_GW_ROOT_DIR/latest
DUMP_DIR=/tmp/oomheap/tomcat/

main()
{

   if [ ! -d $ROOT_DIR ] ; then  
      echo "not exist $ROOT_DIR"   
      exit 1
   fi

   DATE=$(date +%Y-%m-%d-%H-%M-%S)
   CUR_VERSION_DIR=$EASYBIKE_GW_ROOT_DIR/dev-${DATE}
   echo "start to install AppHellobikeBosGW to $CUR_VERSION_DIR"

   mkdir -p $DUMP_DIR
   mkdir -p $CUR_VERSION_DIR

   cp -f init.script $CUR_VERSION_DIR
   cp -f logrotate $CUR_VERSION_DIR
   cp -r webapps $CUR_VERSION_DIR
   cp -r work $CUR_VERSION_DIR
   cp -r conf $CUR_VERSION_DIR
   cp -r temp $CUR_VERSION_DIR

   chmod +x $CUR_VERSION_DIR/*

   if [ -L $EASYBIKE_GW_DEFAULT_DIR ] ;then
       unlink $EASYBIKE_GW_DEFAULT_DIR
   fi

   if  [ -d $EASYBIKE_GW_DEFAULT_DIR ]  || [ -f  $EASYBIKE_GW_DEFAULT_DIR ] ; then
       rm -rf $EASYBIKE_GW_DEFAULT_DIR
   fi

   ln -s $CUR_VERSION_DIR $EASYBIKE_GW_DEFAULT_DIR

   echo "finished to install AppHellobikeBosGW to $CUR_VERSION_DIR"
}

main
