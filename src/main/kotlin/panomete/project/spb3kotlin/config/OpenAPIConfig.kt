package panomete.project.spb3kotlin.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "New Identity API Document",
        version = "0.1-dev",
        description = "Playground for generating new identities"
    )
)
class OpenAPIConfig