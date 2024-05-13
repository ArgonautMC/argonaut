package kr.argonaut.argonaut.infra.github

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class GithubService(
    private val objectMapper: ObjectMapper,
    private val githubClient: GithubClient,
) {
    fun <T> readFile(
        organizationId: String,
        repositoryId: String,
        branch: String?,
        path: String,
        clazz: Class<T>
    ): T {
        try {
            val content = githubClient.github.getOrganization(organizationId)
                .getRepository(repositoryId)
                .run {
                    if(branch == null) getFileContent(path)
                    else getFileContent(path, branch)
                }
            return objectMapper.readValue(content.read(), clazz)
        } catch (e: IOException) {
            throw RuntimeException("failed to get json file! - ${e.message}", e)
        }
    }
}
