cd ..
cd ..
java -jar dist/MM-NEATv2.jar runNumber:0 randomSeed:0 base:multiplelivesconflict maxGens:200 mu:100 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.mspacman.MsPacManTask highLevel:true infiniteEdibleTime:false imprisonedWhileEdible:false pacManLevelTimeLimit:30000 pacmanInputOutputMediator:edu.utexas.cs.nn.tasks.mspacman.sensors.mediators.IICheckEachDirectionMediator trials:10 log:MultipleLivesConflict-MMP saveTo:MMP fs:false edibleTime:200 trapped:true pacManGainsLives:true pacmanLives:3 mmpRate:0.1
