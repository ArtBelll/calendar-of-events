# Install

## Install Java
```bash
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
javac -version
```

## Install PostgreSQL
```bash
sudo apt-get install postgresql
```

## Create database
```bash
sudo su - postgres
createuser --pwprompt --interactive ceserver #password: myAwesomePassword
createdb -Oceserver -Eutf8 ceserver --template=template0
exit
```

## Install Redis
```bash
wget http://download.redis.io/releases/redis-4.0.9.tar.gz
tar xzf redis-4.0.9.tar.gz
cd redis-4.0.9
make
make test
make install

# find and uncomment bind 127.0.0.1 string
# add password
# requirepass SWz23MBi3XVxbjCD
```

## Install Node.JS, PM2
```bash
curl -sL https://deb.nodesource.com/setup_9.x | sudo -E bash -
sudo apt-get install -y nodejs
sudo npm install -g pm2
```

## Prepare logs directory
```bash
sudo mkdir /var/log/ce-server
sudo chown -R user:user /var/log/ce-server
```