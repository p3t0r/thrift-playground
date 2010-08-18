* make sure thrift 0.3 is installed from http://incubator.apache.org/thrift/download/
* install libthrift contained in the thrift download into your local maven repository using

    mvn install:install-file -Dfile=libthrift.jar -DgroupId=com.apache.thrift -DartifactId=thrift -Dversion=0.3 -Dpackaging=jar

* com.baskok.thriftsample.server.Server starts a multithreaded server with the binary protocol
* com.baskok.thriftsample.client.ServiceClient consumes that service
* com.baskok.thriftsample.client.RunPerformanceTest hammers that service
* com.baskok.thriftsample.server.http.HelloServiceServlet is work in progress for creating a debugging interface for the service
