	
	bat文件中如何指定运行一个java class,注意执行的java class必须有main method 入口。
	
	命令如下：
	语法： %JAVA_HOME%\binjava.exe -classpath jarPath java_class_package_path 参数args[]
	解释：
		 1. %JAVA_HOME%\binjava.exe 系统环境JAVA_HOME
		 2. -classpath 原始本身参数名
		 3. jarPath 需要加载到java class path中的jar，一般指一系列的jar,它是-classpath参数的值
		 4. java_class_package_path 指需要进行bat运行的java class,此java类必须有一个main method入口，用于进行命令的执行
		 5. 参数args[] 此参数的内容是一个数组，内容以空格分隔，这些参数会加载到要执行的java class main method的 String args[]中，用于执行java main method的参数使用。
		 
	eg：
		"%JAVA_HOME%\bin\java.exe" -classpath %HPSC_NEXT_TOOL_CLASSPATH% 
		com.hp.hpsc.tools.installer.deploy.DeployMain 
		"%HPSC_NEXT_PACKAGE_LOCATION%" %_DEPLOY_MODE% "%HPSC_NEXT_DATA%\deploy_fingerprint.txt" "%_DEPLOY_TARGETS%"
		
		解释：
		     1. "%JAVA_HOME%\bin\java.exe"=JAVA_HOME
			 2. -classpath 参数名
			 3. %HPSC_NEXT_TOOL_CLASSPATH%指明要加载的jar
			 4. com.hp.hpsc.tools.installer.deploy.DeployMain 要执行的java class
			 5. "%HPSC_NEXT_PACKAGE_LOCATION%" %_DEPLOY_MODE% "%HPSC_NEXT_DATA%\deploy_fingerprint.txt" "%_DEPLOY_TARGETS%" 均为执行参数，
			          在这里有四个参数，也就是说String args[]数组中有四条记录