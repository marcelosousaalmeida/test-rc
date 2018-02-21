def createRelease(tagName, commitID, repo, owner="JumiaAIG", name="", body="", draft=false, prerelease=false) {
    if (name == "") {
        name = tagName
    }

    withCredentials([usernamePassword(credentialsId: 'jumia-integrations-token', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]) {
        String callAPI = """
        set +x && \
        curl -f -s -m 10 -X POST \
            --user ${GIT_USERNAME}:${GIT_PASSWORD} \
            --url https://api.github.com/repos/${owner}/${repo}/releases \
            --header "Content-Type: application/json" \
            --data '{
                "tag_name": "${tagName}",
                "target_commitish": "${commitID}",
                "name": "${name}","body": "${body}",
                "draft": ${draft},
                "prerelease": ${prerelease}
            }'
        """

        status = sh(returnStatus: true, script: callAPI)
    }

    return status
}

this
