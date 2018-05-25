consumerEnv=$1
userName=$2
password=$3
echo '+++++++++++consumerEnv++++++++++++'$consumerEnv
echo '+++++++++++userName++++++++++++'$userName
echo '+++++++++++password++++++++++++'$password

env='not.assignedyet'
if [ '$consumerEnv'=='dev' ]
then
	echo '***********in if********************'
	env='dev'
	echo '=============================='$env
	
else
	echo '***********in else********************'
	env='TSYS'
	echo '=============================='$env
	
fi


# INPUT FILE
# ======================================================================
Data_File="C:\\Users\\asing42\\test\\Data.csv"
echo '=============================='$Data_File

# Output FILE
# ======================================================================
Output_FILE="C:\\Users\\asing42\\test\\team.txt"


java -Xmx100m -Dspring.profiles.active=$env -Dspring.datasource.username=$userName -DinpFile=$Data_File -DoutFile=$Output_FILE -Dspring.datasource.password=$password -jar ./build/libs/SpringBatchWithBoot*.jar --spring.batch.job.names=Job1