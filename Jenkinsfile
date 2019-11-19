pipeline {
 agent any
 stages{
    stage('Cleanup'){
        steps{
            deleteDir()
        }
    }
    stage('Pull project from bitbucket'){
      steps{
        git branch: 'master',
        //credentialsId: 'none',
        url: 'https://bitbucket.org/MichailZhurylo/auto-aid-adviser-back-end.git'
        }
    }
    stage('Build preparation') {
     steps{
        sh 'echo "Build preparation"'
        sh 'chmod +x gradlew'
        }
    }
    stage('Running test'){
     steps{
        sh 'echo "COMMENTED FOR NOW.Running tests"'
        //sh './gradlew test'
        }
    }
    stage('Building project'){
     steps{
        sh 'echo "Building project"'
        sh './gradlew clean build -x test'
        }
    }
    stage('Deploy to S3'){
     steps{
        sh '''
            filename=auto-aid-adviser-5.6.2.jar
            region='eu-west-1b'
            buildbucket='auto-aid-adviser-bucket'
            buildbucketfolder='build'
            aws s3 cp build/libs/${filename} s3://${buildbucket}/${buildbucketfolder}/
        '''
         }
        }
    stage('Reboot E2 instance'){
     steps{
        sh '''
            ids=i-052630e2d111d0bed
            region='eu-west-1b'
            #aws ec2 reboot-instances --instance-ids ${ids} --region ${region}
        '''
         }
        }
    }
     post {

        success {
            slackSend (color: '#00FF00', 
                       message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})", 
                       channel: 'auto-aid-adviser-backend')
        }

        failure {
            slackSend (color: '#FF0000', 
                       message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})", 
                       channel: 'auto-aid-adviser-backend')
        }
    }
}