cd ..
cd ..
java -jar dist/MM-NEATv2.jar runNumber:%1 randomSeed:%1 base:sunset1 trials:1 maxGens:1000 mu:100 io:true netio:true mating:true fs:false task:edu.utexas.cs.nn.tasks.testmatch.imagematch.ImageMatchTask log:sunset1-Control saveTo:Control matchImageFile:sunset1.png allowMultipleFunctions:true ftype:0 watch:false netChangeActivationRate:0.3 overrideImageSize:false imageHeight:200 imageWidth:300 saveAllChampions:true
