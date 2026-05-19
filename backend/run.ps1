param(
    [int]$Port = 8080
)

$ErrorActionPreference = "Stop"

if (-not $env:JAVA_HOME) {
    $defaultJavaHome = "C:\Program Files\Java\jdk-17"
    if (Test-Path $defaultJavaHome) {
        $env:JAVA_HOME = $defaultJavaHome
    }
}

$maven = "mvn"
$localMaven = "C:\Program Files\apache-maven-3.9.16\bin\mvn.cmd"
if (Test-Path $localMaven) {
    $maven = $localMaven
}

& $maven package -DskipTests
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

& "$env:JAVA_HOME\bin\java.exe" -jar "target\study-spot-backend-0.0.1-SNAPSHOT.jar" "--server.port=$Port"
