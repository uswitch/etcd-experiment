#!/bin/bash
export LEIN="/usr/local/bin/lein"
export APP_NAME="etcd-experiment"
export APP_DIR="/var/www/${APP_NAME}/current"
export APP_ENV="production"
export APP_SHARED_DIR="/var/www/${APP_NAME}/shared"

cd $APP_DIR && java -jar $APP_DIR/target/${APP_NAME}-0.1.0-SNAPSHOT-standalone.jar >> $APP_SHARED_DIR/log/${APP_NAME}.log 2>&1
