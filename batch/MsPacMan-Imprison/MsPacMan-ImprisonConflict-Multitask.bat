cd ..
cd ..
java -jar dist/MM-NEATv2.jar runNumber:0 randomSeed:0 base:imprisonconflict maxGens:200 mu:50 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.mspacman.MsPacManTask highLevel:true infiniteEdibleTime:false imprisonedWhileEdible:true pacManLevelTimeLimit:8000 pacmanInputOutputMediator:edu.utexas.cs.nn.tasks.mspacman.sensors.mediators.IICheckEachDirectionMediator trials:10 log:ImprisonConflict-Multitask saveTo:Multitask fs:false edibleTime:200 trapped:true multitaskModes:2 pacmanMultitaskScheme:edu.utexas.cs.nn.tasks.mspacman.multitask.AnyGhostEdibleModeSelector
