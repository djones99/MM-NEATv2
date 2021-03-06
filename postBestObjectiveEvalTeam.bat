REM Usage:   postBestObjectiveEvalTeam.bat <experiment directory> <log prefix> <run type> <run number> <number of trials per team> <teammate.xml> <teammate.xml> <teammate.xml>...
REM Example: postBestObjectiveEvalTeam.bat toruspred TorusPred CoOpMultiCCQ 0 10 gen500_bestIn2.xml gen500_bestIn0.xml gen500_bestIn2.xml
setlocal enabledelayedexpansion
set string=java -jar "dist/MM-NEATv2.jar" runNumber:%4 parallelEvaluations:false base:%1 log:%2-%3 saveTo:%3 trials:%5 watch:false showNetworks:false io:false netio:false onlyWatchPareto:true printFitness:true animateNetwork:false ucb1Evaluation:false showSubnetAnalysis:false monitorInputs:false experiment:edu.utexas.cs.nn.experiment.post.ObjectiveBestTeamsExperiment logLock:true watchLastBestOfTeams:true rlGluePort:4200
shift
shift
shift
shift
shift
set argCount=0
for %%x in (%*) do set /A argCount+=1
set /a argCount=!argCount!-5
echo "Here is the number of team members:" !argCount!
for /l %%x in (1, 1, !argCount!) do (
set string=!string! coevolvedNet%%x:
set string=!string!%%%%x
)
call !string!