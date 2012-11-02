Slimtimer4j client
==================

A basic library to support the slimtimer.com API - at its most basic:

SlimTimer timer = new SlimTimer("user","pass","apiKey");
List<Task> tasks = timer.getAllTasks();

See SlimTimerClient.java for a more comprehensive set of features available (pretty
much whatever the slimtimer API provides, this library provides access to it).

This library does have some dependencies - see the pom.xml for details.

You can use groovy grapes to include this library:

    @GrabResolver(name='danes-mvn-repo', root='https://github.com/dsummersl/dsummersl-mvn-repo/raw/master/snapshots')
    @Grab(group='biz.pinedesk.slimtimer', module='slimtimer4j', version='1.0-SNAPSHOT')

Or you can use maven:

    <repository>
        <id>dsummersl-mvn-repo</id>
        <name>dsummersl repo</name>
        <url>https://github.com/dsummersl/dsummersl-mvn-repo/raw/master/snapshots</url>
    </repository>

    ...
    ...

    <dependency>
        <groupId>biz.pinedesk.slimtimer</groupId>
        <artifactId>slimtimer4j</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>


Building
========

You can build the jar itself:

mvn clean install

You can build a binary script that demos the features:

mvn package appassembler:assemble -DapiKey=<your api key> -Duser=<your user> -Dpassword=<your password>
sh target/appassembler/bin/slimtimer4j

TODO
====

- more examples
- convenience methods to filter TimeEntries by their status (ie fine current active entries easily).
