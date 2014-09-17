require "bundler/capistrano"
require 'capify-ec2/capistrano'

set :application, "etcd-experiment"

set :user, "deploy"
set :keep_releases, 3
set :deploy_to,           "/var/www/#{application}"
set :repository_cache,    "#{application}_cache"
set :environment,         "production"
set :rails_env,           "production"

set :scm, :git
set :repository,  "git@github.com:uswitch/etcd-experiment.git"
set :use_sudo, false
set :deploy_via, :remote_cache
set :branch, "master"
set :scm_verbose, true
set :normalize_asset_timestamps, false
#set :selfdeploy_tag, "inproduction"

ec2_roles :name => "etcdexperiment", :options => {:default => true}

# experimental (untested)
namespace :confd do
  task :setup do
    run("for i in #{current_path}/config/production/conf.d/* ; do    sudo ln -sf ${i} /etc/confd/conf.d/`basename ${i}`    ; done")
    run("for i in #{current_path}/config/production/templates/* ; do sudo ln -sf ${i} /etc/confd/templates/`basename ${i}` ; done")
  end

  after "deploy:create_symlink", "confd:setup"
end

namespace :deploy do
  task :start do
    run("#{current_path}/bin/run.sh > /dev/null 2>&1 &")
  end

  task :stop do
  end

 task :restart, :roles => :app, :except => { :no_release => true } do
   stop
   start
 end

  desc "Build application as an uber jar"
  task :build_uber_jar do
    run("cd #{release_path}; APP_ENV=production lein uberjar")
  end

  after "deploy:create_symlink", "deploy:build_uber_jar"
end
