cd ..
cd ..
java -jar dist/MM-NEATv2.jar runNumber:0 randomSeed:0 base:onelifesplit maxGens:200 mu:100 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.mspacman.MsPacManTask highLevel:true infiniteEdibleTime:false imprisonedWhileEdible:false pacManLevelTimeLimit:8000 pacmanInputOutputMediator:edu.utexas.cs.nn.tasks.mspacman.sensors.mediators.IICheckEachDirectionMediator trials:10 log:OneLifeSplit-MMD saveTo:MMD fs:false edibleTime:200 trapped:true specificGhostEdibleThreatSplit:true specificGhostProximityOrder:false specific:false mmdRate:0.1
