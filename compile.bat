call mvn clean
call mvn package
call cd target
call ren "VaulCard-1.0.0-SNAPSHOT.jar" "walletforalfhonusatm.jar"
call cd ..
call cmd /k