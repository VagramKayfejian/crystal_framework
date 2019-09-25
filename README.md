![alt tag](https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcR--0RgJgYuBqUIYmGY736VbfgtLgelzpjsaIvonnEM-ZB_cLL7)

# ARMONDSARKISIAN.COM

###### APPLICATION: crystal framework + tests
###### AUTHOR(S): Armond Sarkisian
###### MAINTAINER(S): Armond Sarkisian
###### EMAIL: armond.sarkisian@gmail.com
###### COMPLETION DATE: 2019-08-20
###### APPROVAL DATE: 2019-09-04
###### VERSION: 2.0
###### REVISION: 0.4
###### GIT REPO: https://github.com/asarkisian/crystal
###### GIT BRANCHES: master
###### DESCRIPTION:
crystal is a UI test tool that allows the QA team to validate our entire desktop test regression suite. It navigates to each specified page, verifies pre-specified checks, inspects conditions and validates the functionality of the page. It verifies every object and asserts for expected conditions and outcomes.

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
  - stage  (https://)
  - test   (https://)
  - dev    (https://)
  - branch (https://)
  
# DIRECTORY HIERARCHY:
  - src         ==> Contains all the source test files
  - results     ==> Output for LOG/HAR files
  - test-output ==> Output for TestNG reports
  - resources   ==> Project resource properties
  - userData    ==> User generated data
  - target      ==> Build target

# CONFIG FILES:
  - pom.xml
  - build_template.xml
  - adhoc_template.xml
  - setup_run.sh
  - log4j.properties
  - docker-compose.yml

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
$ ssh-keygen -C "$input_email" -b 4096 -t rsa -N "" -f ~/.ssh/$(echo $USER | tr '[:upper:]' '[:lower:]')_id_rsa
$ eval "$(ssh-agent -s)"
$ SSH_USER=~/.ssh/"$(echo $USER | tr '[:upper:]' '[:lower:]')_id_rsa"
$ ssh-add $SSH_USER
$ sudo chown $USER /etc/ssh/ssh_config
$ sudo echo "   IdentityFile $SSH_USER" >> /etc/ssh/ssh_config
$ cat ~/.ssh/id_rsa.pub | pbcopy
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
$ git clone https://github.com/asarkisian/crystal  # clone project
$ cd crystal                                       # access repo root
```

12. Open IDE (i.e., Eclipse)
13. Import Maven Project - 'crystal' that is located within the crystal folder
14. Go through the wizard to detect pom.xml
15. Update and include all required plugins
16. Edit BuildPath for the projects and ensure the version of JDK is 1.8+
17. If on Windows, create a user environmental variable called HOME and set that to C:\Users\name_of_your_user
18. Open the testng_template.xml file to confirm all hardcoded values are correct. Do not alter the variables that start and end with __
19. Set QA_ENV environmental variable to the url of your desired test location. If not specified, it will default to PROD env
20. You can set it in your shell startup file or in the CLI: export QA_CRYSTAL_TEST_ENV="url_goes_here"
21. Open setup_run.sh and configure the run based on your needs

``` sh
$ export QA_CRYSTAL_TEST_ENV="https://"        # this sets environment to the specified url
$ cd crystal                                   # access repo root
$ vim setup_run.sh                             # configure your run
$ ls *.xml                                     # verify your generated file exists
$ sh setup_run.sh                              # execute testng.xml generation script
```

23. Run the shell script (execute on Cygwin if on Windows)
24. Open the build.xml file to confirm all settings are correct
25. Clean the build and make sure all dependencies are detected
26. Resolve any conflicts and run the project
27. If all goes well, you will see the automation load the browser and begin testing

### VIEW GENERATED LOG/REPORTS:

28. If proxy server was enabled and inspecting for HAR files, you can navigate to the logs folder and view the generated HAR files
29. If checking for test completion status and outcomes, you can navigate to test-output folder and open up the generated HTML reports
30. If checking for run-time logs, you can navigate to the logs folder and view the generated data file
31. If checking for log4j events, you can view the log4j.out output file

----------------------------------------------------------------------------------------------------------------

### SELENIUM GRID: SPAWNING NEW INSTANCES:
To enable remote, simply access either the adhoc_template.xml or setup_run.sh file and under parameters "is_remote" select true. You will need to open up multiple terminal sessions. The one I recommend is tmux - https://github.com/tmux/tmux/wiki "terminal multiplexer". You will need to start the Selenium server by specifying the path to the JAR file, hub and the respective port. You will then need to register a node with the provided ip address. 

#### GRID CONSOLE:
If the server is up and running, you can visit the Selenium console at: http://ip:port/grid/console. 

#### HUB ACCESS:
To visit the hub, visit: http://ip:port/wd/hub.

#### HUB STATUS:
To check the status of incoming connections, visit: http://ip:port/wd/hub/status which will yield a JSON file informing you the latest server status

----------------------------------------------------------------------------------------------------------------

### DOCKERIZATION:
The most simple way to start a grid is with docker-compose, use the following snippet as your docker-compose.yaml, save it locally and in the same folder run docker-compose up.

``` sh
$ docker-compose up                            # setup/run the environment
```

docker-compose.yml contents:

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

You can alternatively run docker as follows by creating the grid first:

``` sh
$ ./docker_create_selenium_grid.sh                # setup the grid
```

and then execuring the command to start the grid server

``` sh
$ ./docker_run_selenium_hub_3.141.59_uranium.sh   # execute the grid
```

To run the nodes, you can type:

``` sh
$ ./docker_run_node_chrome_3.141.59_uranium.shh   # if you wish to run the chrome node
$ ./docker_run_node_firefox_3.141.59_uranium.sh   # if you wish to run the firefox node
```

To stop the containers, removes containers, networks, volumes, and images created by up, run the following command:

``` sh
$ docker-compose down                          # Stop containers, remove containers, networks, volumes and images
```

If not using docker-compose up, you can alternatively use the following command:

``` sh
$ ./docker_reset.sh                            # Resets the entire environment
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

### TODOS:
 - Expand on the crystal libraries
 - Refactor all existing classes
 - Add support for more browsers
 - Run tests using different browsers
