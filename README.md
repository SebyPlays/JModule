# JModule

What is the JModule - api?
-
The JModule - api allows you to create external modules for your current java project.


How do i use the JModule - api?
-
Step 1: Create a host project, means the project you want to add modules in and execute the method ,,new ModuleLoader().loadModules();.

Step 2: Build your host project as an artifact and use it as an api for your module.

Step 3: Create a "module.yml" file within the source directory

Step 4: Describe the module with the following necessary attributes


Required attributes:

main: "com.github.example.project.MainClass"

name: "NameOfModule"


Optional attributes:

version: "version"

author: "Seby_Plays"

description: "This is a thing i made and its really great.."


Step 5: create a main class and extend it with the "JavaModule" class (Inherits the necessary methods)

Step 6: implement the "onEnable()" method,


The "onEnable()" method is being executed, when the host loads the module.


Step 7: Do your stuff and don't forget to register everything

Step 8: Build and drag it into your "modules" folder

Step 9: Run your project.

Step 10: Have fun!


12/2/2020
-
Bug found!
The ModuleLoader is *NOT* loading external classes,
i found out that it gets its resources from the internal.

What it exactly should do is, it should load the external main class and execute its methods
in the given order:

onLoad();
onEnable();
onDisable(); (if disabling)

but what it currently does is, it is loading the plugin  description resources, but not the classes..
it loads its class.

Look at the code in the ModuleLoader class..
everything should be mentioned there.

11/29/2020
-
The module api has now full functionality and is ready to be used.

The instructions on how to use it are given in the very top of the ReadMe.md.

11/28/2020
-
The module api is currently *NOT* working.
The ModuleLoader isn't finalized yet..

I have just uploaded my project as an project hub to sync between devices.
 ^^

The functionality is incoming in further updates.
