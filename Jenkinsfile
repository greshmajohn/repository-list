pipeline {

	agent any
	
	tools{
		maven "Maven"
		jdk 'jdk17' 
	}
	
	boolean buildSuccess = false:
	
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
					withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'dockerHub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
 						bat 'docker login -u $USERNAME -p $PASSWORD docker.io'
						bat 'docker tag emp-insurance $USERNAME/repository-list'
						bat 'docker push $USERNAME/repository-list:latest'
						buildSuccess=true;
					}
					
					/*bat 'docker login -u "greshmajithin" -p "Jinkuttan@2017" docker.io'
					bat 'docker tag emp-insurance greshmajithin/repository-list'
					bat 'docker push greshmajithin/repository-list:latest'
					buildSuccess=true;*/
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
        			if(buildSuccess){
        				mail bcc: 'greshmaj99@gmail.com', body: "Deployment successful."+"\n"+"Job : '${env.JOB_NAME}'"+"\n"+"Build : '${env.BUILD_NUMBER}'"+"\n"+"Url : ${env.BUILD_URL}" , cc: '', from: '', replyTo: '', subject: "SUCCESSFUL: '${env.JOB_NAME}' ", to: ''
        				
        			}else{
        				mail bcc: 'greshmaj99@gmail.com', body: "Deployment Failed."+"\n"+"Job : '${env.JOB_NAME}'"+"\n"+"Build : '${env.BUILD_NUMBER}'"+"\n"+"Url : ${env.BUILD_URL}" , cc: '', from: '', replyTo: '', subject: "SUCCESSFUL: '${env.JOB_NAME}' ", to: ''
        				
        			}
        			
        		}
        }
 
  
}

