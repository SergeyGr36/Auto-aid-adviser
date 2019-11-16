node {
​
    stage('Cleanup'){
        deleteDir()
    }
​
    stage('Pull project from bitbucket'){
        git branch: 'testing',
        credentialsId: 'credentials_id_in_jenkins',
        url: 'git@repository.com:user/repo-name.git'
    }
​
    stage('Build preparation') {
        sh 'echo "Build preparation"'
        sh 'chmod +x gradlew'
    }
​
    stage('Running test'){
        sh 'echo "Running tests"'
        sh './gradlew test'
    }
​
    stage('Building project'){
        sh 'echo "Building project"'
        sh './gradlew clean build -x test'
    }
​
    stage('Deploy to S3'){
        sh '''
            filename=your-jar-name.jar
            region='us-east-1'
            buildbucket='bucket-name'
            buildbucketfolder='dir-inside-bucket'
​
            aws s3 cp build/libs/${filename} s3://${buildbucket}/${buildbucketfolder}/${filename} --region ${region}
        '''
    }
}