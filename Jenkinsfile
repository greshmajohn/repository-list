
pipeline {
	
	
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
		stage('test') {
   			steps{
				 bat "mvn test -Punit"
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
					withCredentials([usernamePassword(credentialsId: 'dockerHub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
  
  						bat 'echo $PASSWORD'
  
 						 echo USERNAME
  
  						echo "username is $USERNAME"
					}
					
					
					bat 'docker login -u "greshmajithin" -p "Jinkuttan@2017" docker.io'
					bat 'docker tag emp-insurance greshmajithin/repository-list'
					bat 'docker push greshmajithin/repository-list:latest'
					buildSuccess=true;
				}
    		  
   		 }
   		 stage('Docker Clean up') {
   		 
   		 	steps{
   		 		echo "Delete all dangling resources"
   		 		bat 'docker  system prune'
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

