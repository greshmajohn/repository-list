
pipeline {

	environment {     
    	DOCKERHUB_CREDENTIALS= credentials('dockerHub')   
    	scannerHome = tool 'SonarQubeScanner'  
	} 
	
	
	agent any
	
	tools{
		maven "Maven"
		jdk 'jdk17' 
		
	}
	

	
	stages{
	
		stage('Clone project') {
		
   			steps{
				checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/greshmajohn/repository-list.git']])
			}
		}
		
		stage('Build') {
   			steps{
				 bat 'mvn clean install -DskipTests'
			}
		}
		stage('Sonar scan and quality gate') {
   			steps{
				 withSonarQubeEnv('SonarQube',credentialsId: 'sqa_6ae6e7978548e190725e41f56860e196d2173e3a') {
           		 	bat "${scannerHome}/bin/sonar-scanner"
       			 }
        		timeout(time: 10, unit: 'MINUTES') {
            		waitForQualityGate abortPipeline: true
       			 }
			}
		}
		
      	
		stage('Docker Build') {
				steps{
					echo "Build docker image"
					
					bat 'docker build -t repository-list:latest .'
					
				}
    		  
   		 }
   		 stage('Docker Deployment') {
				steps{
					echo "docker deployment"
					bat 'echo $DOCKERHUB_CREDENTIALS_PSW  $DOCKERHUB_CREDENTIALS_USR ' 
					withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'password', usernameVariable: 'username')]) {
        			    print 'username=' + username + 'password=' + password

          				  print 'username.collect { it }=' + username.collect { it }
           				 print 'password.collect { it }=' + password.collect { it }
           				 
           				 bat 'docker login -u ' +username+' -p '+ password +' docker.io'
           				 bat 'docker tag emp-insurance '+username+'/repository-list'
						 bat 'docker push '+username+'/repository-list:latest'
         			 }
        		  
						
  						
						
					
				}
    		  
   		 }
   		 
   		 
   		
	}
	 post {
       		 always {
        			echo "Delete unwanted resources"
        			deleteDir() /* clean up our workspace */
        			bat 'docker  system prune'
        			
        		}
        	success {
           		 echo 'Deployment successful !'
           		 mail bcc: 'greshmaj99@gmail.com', body: "Deployment Success" +"\n"+"Job : '${env.JOB_NAME}'"+"\n"+"Build : '${env.BUILD_NUMBER}'"+"\n"+"Url : ${env.BUILD_URL}" , cc: '', from: '', replyTo: '', subject: "SUCCESSFUL: '${env.JOB_NAME}' ", to: ''
        		
      		  }
        	unstable {
            	echo 'Deployment Instable.'
       		 }
       		 failure {
          		  echo 'Deployment Failed'
          		  mail bcc: 'greshmaj99@gmail.com', body: "Deployment Failed" +"\n"+"Job : '${env.JOB_NAME}'"+"\n"+"Build : '${env.BUILD_NUMBER}'"+"\n"+"Url : ${env.BUILD_URL}" , cc: '', from: '', replyTo: '', subject: "FAILED: '${env.JOB_NAME}' ", to: ''
        		
       		 }
        
        }
 
  
}

