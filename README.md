# etcd-experiment

An experiment in using [etcd](https://github.com/coreos/etcd) and [confd](https://github.com/kelseyhightower/confd) in an attempt to have zero downtime deployment for [Clojure Ring](https://github.com/ring-clojure/ring) applications.

## Running the code on a laptop

This guide assumes a Mac OS X environment with [homebrew](http://brew.sh/) properly configured; please tailor instructions to your environment.

To install and start etcd:

```
brew install etcd
launchctl load /usr/local/opt/etcd/homebrew.mxcl.etcd.plist
```

The distribution of confd doesn't include a wrapping directory so to install it we need to do a little bit more:

```
mkdir confd ; cd confd
wget https://github.com/kelseyhightower/confd/releases/download/v0.3.0/confd_0.3.0_darwin_amd64.zip
unzip confd_0.3.0_darwin_amd64.zip
```

In the root of the project directory do the following:

```
mkdir output
touch output/nginx.conf output/restart-service.sh
./confd/confd --confdir="config/development" --interval=5
```

This starts confd with the development configuration under `conf/development` and causes it to check etcd every 5 seconds.

Open two other terminals and in one do `tail -F output/nginx.conf` and in the other `tail -F output/restart-service.sh`.  These will show the changes to these files as you start services so it is best to keep these in the foreground.

You should now be able to start, in one terminal, the service with `lein run`.  Once the service has started open a second terminal and start the service again.  The first service instance should exit fairly soon after the second one starts.

## License

Copyright © 2014 uSwitch Limited.  All rights reserved.

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
