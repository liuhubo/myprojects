#!/bin/bash
HOST=`fm|grep Host|awk '{print $2}'`
APP=AppHellobikeBosGW
DIR=/workspace/carkey/${APP}/latest/logs
rsync -azv ${DIR}/soa.log*.gz 10.46.64.227::backup/${APP}/${HOST}/
