![alt tag](https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcR--0RgJgYuBqUIYmGY736VbfgtLgelzpjsaIvonnEM-ZB_cLL7)

# ARMONDSARKISIAN.COM

###### APPLICATION: crystal framework
###### AUTHOR(S): Armond Sarkisian
###### MAINTAINER(S): Armond Sarkisian
###### EMAIL: armond.sarkisian@gmail.com
###### COMPLETION DATE: 2019-08-20
###### APPROVAL DATE: 2019-09-04
###### VERSION: 2.0
###### REVISION: 0.4
###### HOMEPAGE: http://www.armondsarkisian.com
###### GITHUB PAGE: https://asarkisian.github.io/crystal_framework/
###### GIT REPO: https://github.com/asarkisian/crystal_framework
###### GIT BRANCHES: master
###### DESCRIPTION:
crystal framework is a test harness that allows the QA team to validate their entire test regression suite in an automated fashion. It navigates to each specified page, verifies objects, inspects content, asserts conditions and validates the functionality of the page. 

# SPECIFICATIONS:

  - Java Dev Kit: Java 1.8.0_211+
  - Test Framework: TestNG 6.10+
  - Assertion Library: TNG Assert 6.10+
  - Unit Library: JUnit 4.11+
  - Logger: Log4J 1.2.17+, Module.Reporting 1.0+
  - Web Driver Server: Selenium Standalone Server 3.141.59+
  - Web Driver Client: Selenium Client 3.141.59+
  - Web Drivers: chromedriver 76.0.3809.126, geckodrive 0.24.0, phantomjs 1.1.0+
  - Proxy Server: BrowserMob Lightbody 2.1.5+
  - API Library: RestAssured 3.30+
  - Serialization: JSON 20140107+, YAML 1.23+

# SUPPORTED ENVIRONMENTS:

  - prod   (https://)
  - uat    (https://)
  - stage  (https://)
  - test   (https://)
  - dev    (https://)
  - branch (https://)
  
# DIRECTORY HIERARCHY:
  - src           ==> Contains all the source test files
  - src/resources ==> Project resource properties
  - results       ==> Output all log files
  - test-output   ==> Output for TestNG reports
  - cache         ==> User generated data
  - target        ==> Build target files

# CONFIG FILES:
  - pom.xml
  - docker-compose.yml  
  - build.xml
  - build_template.xml
  - adhoc_template.xml
  - setup_run.sh
  - log4j.properties
  - log4j2.xml

# TEST SUITES:
  - Test (base)

# TEST ANATOMY/STRUCTURE:
  - Test Module File
  - Test Functionality File
  - Page Objects File
  - Configuration File

### REQUIREMENTS:
* [Java JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html) - Latest Version of Java JDK
* [Java JRE Runtime](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) - Java's latest runtime
* [Eclipse](https://www.eclipse.org/downloads/) - Java based IDE
* [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=mac) - Java based IDE
* [Atom](https://atom.io/) - Text editor
* [TestNG](http://beust.com/eclipse) - TestNG Test Framework for JDK
* [Log4J](https://logging.apache.org/log4j/2.0/download.html) - Log4J Logger
* [Selenium Client](https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java) - Selenium Client
* [Selenium Server](https://bit.ly/2TlkRyu) - Selenium Standalone Server
* [Chromedriver](https://chromedriver.chromium.org/downloads) - Chromedriver for Selenium
* [PhantomJS](https://phantomjs.org/download.html) - PhantomJS for Selenium
* [Geckodriver](https://github.com/mozilla/geckodriver/releases) - Marionette Geckodriver for Selenium
* [GIT](https://git-scm.com/downloads) - GIT
* [Docker](https://www.docker.com/) - Docker

### INSTALLATION & SETUP:
1. Install all requirements mentioned above
2. Navigate to your home/user folder
3. Be sure to have your SSH key setup

```sh
$ ./configureSSH.sh # download from https://github.com/asarkisian/configuration/blob/dev/configureSSH.sh
```
4. Log into github.com
5. Upper-right hand side, click on your profile dropdown
6. Click on "Settings"
7. Click on "SSH and GPG Keys" on the left side
8. Click on "New SSH Key"
9. Type title "GIT SSH Key"
10. Right-click on "Key" Input Text field and PASTE
11. Click on "Add SSH Key" which will complete the process

```sh
$ git clone https://github.com/asarkisian/crystal_framework ~/Development/crystal_framework 
$ cd Development/crystal_framework
$ ls -la
```

12. Open any IDE (i.e., Eclipse, IntelliJ)
13. Import Maven Project - 'crystal_framework' that is located within the Development directory
14. Go through the wizard to detect pom.xml
15. Update and include all required plugins
16. Edit BuildPath and ensure the JDK is 1.8+
17. Open the build_template.xml file to confirm all hardcoded values are correct. Do not alter the variables that start and end with __
18. Set all environmental variables. If not specified, it will set to default values

* QA_CRYSTAL_TEST_BROWSER
* QA_CRYSTAL_TEST_ISHEADLESS
* QA_CRYSTAL_TEST_ISREMOTE
* QA_CRYSTAL_TEST_REMOTEIP
* QA_CRYSTAL_TEST_REMOTEPORT
* QA_CRYSTAL_TEST_ISPROXY
* QA_CRYSTAL_TEST_ENV

19. You can set it in your shell startup file or in the CLI: export QA_CRYSTAL_TEST_ENV="url_goes_here"
20. Open setup_run.sh and configure the run based on your needs. It will generate a build.xml

``` sh
$ export QA_CRYSTAL_TEST_ENV="https://"        # this sets environment to the specified url
$ cd crystal_framework                         # access repo root
$ vim setup_run.sh                             # configure your run
$ sh setup_run.sh                              # execute testng.xml generation script
$ ls -l build.xml                              # verify your file gets generated
```

21. Run the shell script (execute on cygwin, mingw, git-bash, wsl if on Windows)
22. Open the build.xml file to confirm all settings are correct
23. Clean the build and make sure all dependencies are detected
24. You can clean using cmd: mvn clean
25. You can compile using cmd: mvn compile
26. Resolve any conflicts and run the project
27. If all goes well, you will see the automation load the browser and begin testing

### VIEW GENERATED LOG/REPORTS:

28. If proxy server was enabled and inspecting for HAR files, you can navigate to the logs folder and view the generated HAR files
29. If checking for test completion status and outcomes, you can navigate to test-output folder and open up the generated HTML reports
30. If checking for run-time logs, you can navigate to the logs folder and view the generated data file

----------------------------------------------------------------------------------------------------------------

### SELENIUM GRID: SPAWNING NEW INSTANCES:
To enable remote, simply access either the adhoc_template.xml or setup_run.sh file and under parameters "is_remote" select true. You will need to open up multiple terminal sessions. The one I recommend is tmux - https://github.com/tmux/tmux/wiki "terminal multiplexer". You will need to start the Selenium server by specifying the path to the JAR file, hub and the respective port. You will then need to register a node with the provided ip address. 

``` sh
$ java -jar selenium-server-standalone-(version).jar -role hub -port (port)
$ java -Dwebdriver.chrome.driver="path_to_project/drv/chromedriver(.exe)" -jar selenium-server-standalone-(version).jar -role node -hub http://(ip):(port)/grid/register
```

#### SELENIUM GRID: GRID CONSOLE
If the server is up and running, you can visit the Selenium console at: http://ip:port/grid/console. 

#### SELENIUM GRID: HUB ACCESS
To visit the hub, visit: http://ip:port/wd/hub.

#### SELENIUM GRID: HUB STATUS
To check the status of incoming connections, visit: http://ip:port/wd/hub/status which will yield a JSON file informing you the latest server status

### DOCKERIZATION:
Be sure you have docker installed and the daemon running in the background. Once set, you will need to generate a docker-compose.yml file.

To verify if the docker daemon is running, you can execute:

``` sh
$ pgrep -f docker > /dev/null || echo "starting docker"
```

Create a new file called docker-compose.yml and populate it with either version 2 or 3 depending on your needs:

Version 2:

``` sh
# To execute this docker-compose yml file use `docker-compose -f <file_name> up`
# Add the `-d` flag at the end for detached execution
version: '2'
services:
  firefox:
    image: selenium/node-firefox:3.141.59-vanadium
    volumes:
      - /dev/shm:/dev/shm
    depends_on:
      - hub
    environment:
      HUB_HOST: hub

  chrome:
    image: selenium/node-chrome:3.141.59-vanadium
    volumes:
      - /dev/shm:/dev/shm
    depends_on:
      - hub
    environment:
      HUB_HOST: hub

  hub:
    image: selenium/hub:3.141.59-vanadium
    ports:
      - "4444:4444"
```

Version 3:

``` sh
# To execute this docker-compose yml file use `docker-compose -f <file_name> up`
# Add the `-d` flag at the end for detached execution
version: "3"
services:
  selenium-hub:
    image: selenium/hub:3.141.59-uranium
    container_name: selenium-hub
    ports:
      - "4444:4444"
  chrome:
    image: selenium/node-chrome:3.141.59-uranium
    volumes:
      - /dev/shm:/dev/shm
    depends_on:
      - selenium-hub
    environment:
      - HUB_HOST=selenium-hub
      - HUB_PORT=4444
  firefox:
    image: selenium/node-firefox:3.141.59-uranium
    volumes:
      - /dev/shm:/dev/shm
    depends_on:
      - selenium-hub
    environment:
      - HUB_HOST=selenium-hub
      - HUB_PORT=4444
```

To launch the docker container and its environment, run:

``` sh
$ docker-compose up
```

To shutdown the docker container and its environment, run:

``` sh
$ docker-compose down
```

To see what docker processes are currently running:

``` sh
$ docker ps                                    # view all docker related processes
```

To access image management for docker:

``` sh
$ docker image                                 # manage images
```

To access container management for docker:

``` sh
$ docker container                             # manage containers
```

To get a complete list of docker CLI commands, please visit: https://docs.docker.com/engine/reference/commandline/docker/

### PLUGINS:

The following applications/plugins can further aid you in getting the job done effectively and quickly. I especially recommend VIM if you love keyboard shortcuts.

| PLUGIN | README |
| ------ | ------ |
| VIM | [https://www.vim.org/] |
| TMUX | [https://github.com/tmux/tmux/wiki] |
| Vundle Plugin | [https://github.com/VundleVim/Vundle.vim] |
| Eclipse Vrapper Vim Plugin | [http://vrapper.sourceforge.net/home/] |
| IDEA Vim Plugin | [https://plugins.jetbrains.com/plugin/164-ideavim/versions] |
| Firebug Plugin | [https://blog.getfirebug.com/] |
| Firepath Plugin | [https://firepath.en.downloadastro.com/] |
| Omnibug Plugin | [https://chrome.google.com/webstore/detail/omnibug/bknpehncffejahipecakbfkomebjmokl?hl=en] |
| JSON Viewer Plugin | [https://chrome.google.com/webstore/detail/json-viewer/gbmdgpbipfallnflgajpaliibnhdgobh?hl=en-US] |
| JSON/YAML Toggle Plugin | [https://chrome.google.com/webstore/detail/json-yaml-toggle/dphedhpnbojdegjcokghhfploeobobbc?hl=en] |
| iTerm2 Terminal (for Mac) | [https://www.iterm2.com/] |
| Cygwin Terminal (for Win) | [https://www.cygwin.com/] |
| Mingw (for Win) | [http://www.mingw.org/category/wiki/download] |

### TODOS:
 - Expand on the following areas:
  - functional testing
  - performance testing
  - api testing
  - db testing
 - Refactor all existing classes
 - Add support for more browsers
