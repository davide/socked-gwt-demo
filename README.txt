== Install maven
http://askubuntu.com/a/94341

== Install nodejs
sudo apt-get install python-software-properties
sudo add-apt-repository ppa:chris-lea/node.js
sudo apt-get update
sudo apt-get install nodejs

== Install other nodejs dependencies
# https://github.com/sockjs/sockjs-node
npm install socksjs
# http://expressjs.com/guide.html
npm install express

== Build project
mvn clean compile gwt:compile

== Startup server
NODE_ENV=production nodejs src/main/node/server.js

# Open http://localhost:8080/ to check if everything is working.

