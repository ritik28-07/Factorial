package uk.org.winder.maths

import org.testng.annotations.Test
import org.testng.annotations.DataProvider

import static org.testng.Assert.assertEquals

class Test_Factorial_TestNG_Groovy {

    private final algorithms = [
            Factorial.&iterative,
            Factorial.&reductive,
            Factorial.&reductive_alt,
            Factorial.&naïveRecursive,
            Factorial.&tailRecursive,
    ]

    private final positiveData = [
            [0, 1G],
            [1, 1G],
            [2, 2G],
            [3, 6G],
            [4, 24G],
            [5, 120G],
            [6, 720G],
            [7, 5040G],
            [8, 40320G],
            [9, 362880G],
            [10, 3628800G],
            [11, 39916800G],
            [12, 479001600G],
            [13, 6227020800G],
            [14, 87178291200G],
            [20, 2432902008176640000G],
            [30, 265252859812191058636308480000000G],
            [40, 815915283247897734345611269596115894272000000000G],
            [1000, 402387260077093773543702433923003985719374864210714632543799910429938512398629020592044208486969404800479988610197196058631666872994808558901323829669944590997424504087073759918823627727188732519779505950995276120874975462497043601418278094646496291056393887437886487337119181045825783647849977012476632889835955735432513185323958463075557409114262417474349347553428646576611667797396668820291207379143853719588249808126867838374559731746136085379534524221586593201928090878297308431392844403281231558611036976801357304216168747609675871348312025478589320767169132448426236131412508780208000261683151027341827977704784635868170164365024153691398281264810213092761244896359928705114964975419909342221566832572080821333186116811553615836546984046708975602900950537616475847728421889679646244945160765353408198901385442487984959953319101723355556602139450399736280750137837615307127761926849034352625200015888535147331611702103968175921510907788019393178114194545257223865541461062892187960223838971476088506276862967146674697562911234082439208160153780889893964518263243671616762179168909779911903754031274622289988005195444414282012187361745992642956581746628302955570299024324153181617210465832036786906117260158783520751516284225540265170483304226143974286933061690897968482590125458327168226458066526769958652682272807075781391858178889652208164348344825993266043367660176999612831860788386150279465955131156552036093988180612138558600301435694527224206344631797460594682573103790084024432438465657245014402821885252470935190620929023136493273497565513958720559654228749774011413346962715422845862377387538230483865688976461927383814900140767310446640259899490222221765904339901886018566526485061799702356193897017860040811889729918311021171229845901641921068884387121855646124960798722908519296819372388642614839657382291123125024186649353143970137428531926649875337218940694281434118520158014123344828015051399694290153483077644569099073152433278288269864602789864321139083506217095002597389863554277196742822248757586765752344220207573630569498825087968928162753848863396909959826280956121450994871701244516461260379029309120889086942028510640182154399457156805941872748998094254742173582401063677404595741785160829230135358081840096996372524230560855903700624271243416909004153690105933983835777939410970027753472000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000G],
            [2000, 331627509245063324117539338057632403828111720810578039457193543706038077905600822400273230859732592255402352941225834109258084817415293796131386633526343688905634058556163940605117252571870647856393544045405243957467037674108722970434684158343752431580877533645127487995436859247408032408946561507233250652797655757179671536718689359056112815871601717232657156110004214012420433842573712700175883547796899921283528996665853405579854903657366350133386550401172012152635488038268152152246920995206031564418565480675946497051552288205234899995726450814065536678969532101467622671332026831552205194494461618239275204026529722631502574752048296064750927394165856283531779574482876314596450373991327334177263608852490093506621610144459709412707821313732563831572302019949914958316470942774473870327985549674298608839376326824152478834387469595829257740574539837501585815468136294217949972399813599481016556563876034227312912250384709872909626622461971076605931550201895135583165357871492290916779049702247094611937607785165110684432255905648736266530377384650390788049524600712549402614566072254136302754913671583406097831074945282217490781347709693241556111339828051358600690594619965257310741177081519922564516778571458056602185654760952377463016679422488444485798349801548032620829890965857381751888619376692828279888453584639896594213952984465291092009103710046149449915828588050761867924946385180879874512891408019340074625920057098729578599643650655895612410231018690556060308783629110505601245908998383410799367902052076858669183477906558544700148692656924631933337612428097420067172846361939249698628468719993450393889367270487127172734561700354867477509102955523953547941107421913301356819541091941462766417542161587625262858089801222443890248677182054959415751991701271767571787495861619665931878855141835782092601482071777331735396034304969082070589958701381980813035590160762908388574561288217698136182483576739218303118414719133986892842344000779246691209766731651433494437473235636572048844478331854941693030124531676232745367879322847473824485092283139952509732505979127031047683601481191102229253372697693823670057565612400290576043852852902937606479533458179666123839605262549107186663869354766108455046198102084050635827676526589492393249519685954171672419329530683673495544004586359838161043059449826627530605423580755894108278880427825951089880635410567917950974017780688782869810219010900148352061688883720250310665922068601483649830532782088263536558043605686781284169217133047141176312175895777122637584753123517230990549829210134687304205898014418063875382664169897704237759406280877253702265426530580862379301422675821187143502918637636340300173251818262076039747369595202642632364145446851113427202150458383851010136941313034856221916631623892632765815355011276307825059969158824533457435437863683173730673296589355199694458236873508830278657700879749889992343555566240682834763784685183844973648873952475103224222110561201295829657191368108693825475764118886879346725191246192151144738836269591643672490071653428228152661247800463922544945170363723627940757784542091048305461656190622174286981602973324046520201992813854882681951007282869701070737500927666487502174775372742351508748246720274170031581122805896178122160747437947510950620938556674581252518376682157712807861499255876132352950422346387878954850885764466136290394127665978044202092281337987115900896264878942413210454925003566670632909441579372986743421470507213588932019580723064781498429522595589012754823971773325722910325760929790733299545056388362640474650245080809469116072632087494143973000704111418595530278827357654819182002449697761111346318195282761590964189790958117338627206088910432945244978535147014112442143055486089639578378347325323595763291438925288393986256273242862775563140463830389168421633113445636309571965978466338551492316196335675355138403425804162919837822266909521770153175338730284610841886554138329171951332117895728541662084823682817932512931237521541926970269703299477643823386483008871530373405666383868294088487730721762268849023084934661194260180272613802108005078215741006054848201347859578102770707780655512772540501674332396066253216415004808772403047611929032210154385353138685538486425570790795341176519571188683739880683895792743749683498142923292196309777090143936843655333359307820181312993455024206044563340578606962471961505603394899523321800434359967256623927196435402872055475012079854331970674797313126813523653744085662263206768837585132782896252333284341812977624697079543436003492343159239674763638912115285406657783646213911247447051255226342701239527018127045491648045932248108858674600952306793175967755581011679940005249806303763141344412269037034987355799916009259248075052485541568266281760815446308305406677412630124441864204108373119093130001154470560277773724378067188899770851056727276781247198832857695844217588895160467868204810010047816462358220838532488134270834079868486632162720208823308727819085378845469131556021728873121907393965209260229101477527080930865364979858554010577450279289814603688431821508637246216967872282169347370599286277112447690920902988320166830170273420259765671709863311216349502171264426827119650264054228231759630874475301847194095524263411498469508073390080000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000G]
    ]

    private final negativeData = [-1, -2, -5, -10, -20, -100]

    private final doubleData = [100.5D, 20.5D, 10.5D, 5.5D, 2.5D, 0.5D, -0.5D, -2.5D, -5.5D, -10.5D, -20.5D, -100.5D]

    @DataProvider
    private Object[][] algorithmsAndPositiveData() {
        algorithms.collectMany { a -> positiveData.collect { [a, *it] } } as Object[][]
    }

    @DataProvider
    private Object[][] algorithmsAndNegativeData() {
        algorithms.collectMany { a -> negativeData.collect { [a, it] } } as Object[][]
    }

    @DataProvider
    private Object[][] algorithmsAndDoubleData() {
        algorithms.collectMany { a -> doubleData.collect { [a, it] } } as Object[][]
    }

    @Test(dataProvider = "algorithmsAndPositiveData")
    public void positiveArgumentShouldWork(Closure f, Integer n, BigInteger expected) {
        assertEquals(f(n), expected)
    }

    @Test(dataProvider = "algorithmsAndNegativeData", expectedExceptions = [IllegalArgumentException])
    public void negativeArgumentShouldThrowException(Closure f, Integer n) { f(n) }

    @Test(dataProvider = "algorithmsAndDoubleData", expectedExceptions = [MissingMethodException])
    public void floatArgumentShouldThrowException(Closure f, Double n) { f(n) }

    @Test
    public void iterativeEnormousSucceeds() { Factorial.iterative(26000) }

    @Test
    public void reductiveEnormousSucceeds() { Factorial.reductive(26000) }

    @Test(expectedExceptions = [StackOverflowError])
    public void recursiveEnormousFails() { Factorial.naïveRecursive(26000) }

    @Test(expectedExceptions = [StackOverflowError])
    public void tailRecursiveEnormousFails() { Factorial.tailRecursive(26000) }
}
