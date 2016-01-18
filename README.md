code by fastdfs-client 1.20
this project version was mapped by fast-client;
/* configuration file in maven */
<plugin>
	<artifactId>maven-jar-plugin</artifactId>
	<version>2.4</version>
	<configuration>
		<archive>
			<manifest>
				<addDefaultImplementationEntries>true</addDefaultImplementationEntries>
				<addClasspath>true</addClasspath>
				<classpathPrefix>xxx/</classpathPrefix>
				<mainClass>xxx.xxx.xxx.xxx</mainClass>
			</manifest>
		</archive>
	</configuration>
</plugin>