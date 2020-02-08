## 更新版本
* mvn versions:set -DnewVersion=1.0.4
* mvn versions:commit
* 如果不一致 mvn -N versions:update-child-modules  
* 在 commit之前, 记得执行 mvn clean install -N

## install or deploy
* 设置 属性 - particle.version to ${version}
* mvn -P up clean install -Dmaven.test.skip=true
