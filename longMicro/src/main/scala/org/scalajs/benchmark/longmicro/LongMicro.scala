/*                     __                                               *\
**     ________ ___   / /  ___      __ ____  Scala.js Benchmarks        **
**    / __/ __// _ | / /  / _ | __ / // __/  (c) 2016, LAMP/EPFL        **
**  __\ \/ /__/ __ |/ /__/ __ |/_// /_\ \                               **
** /____/\___/_/ |_/____/_/ | |__/ /____/                               **
**                          |/____/                                     **
\*                                                                      */

package org.scalajs.benchmark.longmicro

object LongMicroAll extends org.scalajs.benchmark.Benchmark {
  private val allBenches = Array[org.scalajs.benchmark.Benchmark](
      LongNop,
      LongXor,
      LongAdd,
      LongMul,
      LongDiv32_32,
      LongDiv32_28,
      LongDiv32_8,
      LongDiv53_53,
      LongDiv53_49,
      LongDiv53_8,
      LongDiv64_Pow2,
      LongDiv64_64,
      LongDiv64_60,
      LongDiv64_8,
      LongConstantDivAddBaseline,
      LongSignedConstantDiv,
      LongSignedConstantRem,
      LongUnsignedConstantDiv,
      LongUnsignedConstantRem,
      LongSignedConstantDivPow2,
      LongToString32,
      LongToString53,
      LongToString64
  )

  override def main(args: Array[String]): Unit = {
    for (bench <- allBenches)
      bench.main(args)
  }

  override def report(): String =
    allBenches.map(_.report()).mkString("\n")

  def run(): Unit = ???
}

object LongMicroDataSets {

  @FunctionalInterface
  trait LongToLongFun {
    def apply(x: Long): Long
  }

  val random64s = Array[Long](
      0x416a5e6e5a17717dL,
      0x955c952fc04391f0L,
      0xbea6e0dd3fb902beL,
      0xc42a5178e96e6710L,
      0x79d411e4b174ea28L,
      0x83ea77b7a9781e62L,
      0xf602f95ae2f24bdaL,
      0x6f9d1ec5ab9f4f0bL,
      0x692db997f1814ebL,
      0x668e4565cf1d9148L,
      0x8eac575e12838d7aL,
      0x6a7ffcca4afe30a0L,
      0x7b5fdf1850d7803L,
      0xb0ee375b88714d59L,
      0x95b7c98d00b151f4L,
      0x97fa3fba27ab212cL,
      0x201515f8fd4f897fL,
      0x9be81c00a8265b3bL,
      0xf15aff75e6fcbcb2L,
      0xd7f8614d6f1bc742L,
      0x345b4bdcb51d55c4L,
      0x210d3b7f8570b40dL,
      0xdaa42071cae18b0L,
      0x7a31ad433480cd29L,
      0x8118136ef50f7607L,
      0xdf064c4a6e3f5bdeL,
      0x2124bc1525b2b702L,
      0x686fddaa477235cL,
      0x2cf77ca4916556d6L,
      0x75d1778fcbaf4c33L,
      0x1e5152d812e81d4cL,
      0xa6cc8859e3af1308L,
      0x3e841419b344ce90L,
      0x160c6dc899012e85L,
      0xdee0e6cd62e8448L,
      0x2e50b98ab3732091L,
      0xa714538b88e603e8L,
      0x72bc8b31cc9be6c1L,
      0xbb7412535ddca644L,
      0x33b22239aa38be0dL,
      0xe6c5a25d0fe5a466L,
      0x1470c48f9e5edc6L,
      0x53f225ee646edda1L,
      0xf1d673b7a9a4cdc7L,
      0xa7a6e6129a376ae6L,
      0x778b5251c1ff7beaL,
      0xd57fcd07e28b9355L,
      0xad148c5a3eadf4beL,
      0x9074a2d88372f616L,
      0xc45137d1a0a78376L,
      0xf3caf04afab6fd6aL,
      0x29e393f9c3c6bd23L,
      0x7988abfbc974eefcL,
      0x527d751641d57b25L,
      0x648215c807502d7dL,
      0x5aa12874703988dfL,
      0x20df646de08e7f6fL,
      0x43be14acd3d1f8a9L,
      0x5c9d29732cb16f3fL,
      0xca409a29fb20540aL,
      0x74548f4528bd1899L,
      0x73a0f7e9b8d81b26L,
      0x65c6d5a4728df6f8L,
      0x71f538811ba24775L,
      0x8b79b03cdea4897bL,
      0x80466b629289c5b5L,
      0x99686b96f0e6dfffL,
      0x9829ae61fb611b21L,
      0x74a7fc3240456569L,
      0xca21cb74e0c0a9e3L,
      0xbd1a5bed0e317b43L,
      0xf566eb9a34048c6L,
      0xb2fd9a7e2a9ba8aL,
      0x587a38bd5486222fL,
      0xb27b3f292588edc5L,
      0xab8930f985f00e4dL,
      0x26c7f142e3021859L,
      0xd49d5c9f873bdc8fL,
      0x2aaf6e342d051f1bL,
      0xdaf7d968ee64056L,
      0x969dce4eb2ece627L,
      0x59f63dc3fd3f54ebL,
      0x940b2ada1d90f7efL,
      0xf3f64a8a87aeecb3L,
      0x84aa0f38bce20996L,
      0xad7a24953a921fa1L,
      0xc6a9584aeed9f07eL,
      0x16d0a7d238beccebL,
      0x102a261302fcdd1fL,
      0x8b1d8fc137510400L,
      0xaf0c39af332ef1aL,
      0x3a777bee663188b8L,
      0x2861a914279e768dL,
      0x1c4338bbb103e6cdL,
      0xb349fc41408476a9L,
      0x84f45063ac1dc889L,
      0xf305331604a23d80L,
      0x5657283cad1084c1L,
      0xa887bc9b9ca42523L,
      0x59092e3d49691801L
  )

  val random60s = Array[Long](
      0x4f5b188d680addbL,
      0xfaa510d361001425L,
      0xfd33da7e04f9401cL,
      0xf82cc6619f8e536fL,
      0xfaba1877a52151c9L,
      0x5856701b4b198f3L,
      0xfed68d8d0a5b88caL,
      0x69320483fcb7814L,
      0x6a0d0677f371b03L,
      0x360dbbdd6e0001bL,
      0xffd9053b1f3b136dL,
      0xfa6e9f5bf973d562L,
      0xf858408c2d3e5040L,
      0x6f9ef177a52c2fL,
      0x1ee8a53aacc35e5L,
      0x75ace1750dd52cL,
      0x2173058302f3ac2L,
      0x1e31b2879064360L,
      0x7e058bc71ac64a3L,
      0xfe31393cfba4ba5fL,
      0x19d286677b47df7L,
      0x3d1bee49c4d3b9aL,
      0x653fdc694f62329L,
      0x21d0c729959d5caL,
      0xfe6fda208680bf98L,
      0x572bdbe1a27fde0L,
      0xffd49a53f22cd753L,
      0xf8095d9a4cac61d7L,
      0x6314688d545cbacL,
      0x59029a522f5058L,
      0x5957ef79d4f3634L,
      0xfb6b748e2870a0c3L,
      0xf84251c6c3240ebbL,
      0x222e4e4957a01a2L,
      0xfcebbfd16796de64L,
      0x50b935432ce1a22L,
      0xfed45c48edf98d35L,
      0xfe8b936e3e40a849L,
      0xfbf2a895b154302dL,
      0x5aaedff49dc6f77L,
      0x57b5be2a79a4efcL,
      0xfd16838538798d48L,
      0xf9aaa2f7fa4f226bL,
      0x3c33afa8e0bdd44L,
      0x56071d8e06503c1L,
      0xffae10d909ea6d40L,
      0xffd53865cb5c8a7aL,
      0xfb628012a5630cf0L,
      0xfdacad84a1fe6847L,
      0x2a3ab9ead917d95L,
      0x1a0841a08e579c2L,
      0xfaf8e7c367dc4d8eL,
      0xfb0e92027871ab70L,
      0xfd0af08109257439L,
      0x5f6450cb28d18c2L,
      0xfd7b6b5cec0925aeL,
      0xf96affa4e43cfcacL,
      0xfde05391f33ef10bL,
      0x29dc156784efc5fL,
      0x4fe442649bfb627L,
      0xffdff74a68d2a837L,
      0xfeb598c8160b5d0dL,
      0xa4f92e93140ef9L,
      0x6fd547eb2947219L,
      0x21ba3686071c291L,
      0xfeee2adc7de9794dL,
      0xfd3402120042e8cdL,
      0xfc21b8111ae69d77L,
      0xfb7a0fad5de1563cL,
      0xfc51ec0a7dd03fe9L,
      0xfdddb3ce4b94a05cL,
      0xf9ba44d13ffa36ecL,
      0xfcb5dcda3f3dc875L,
      0xfa99f1050ee81743L,
      0xfea5fa827148ab24L,
      0xff83303a51a620dcL,
      0xfb7d7914e18fe80dL,
      0xfa69c3cca19f7631L,
      0xfe8f8a664d41b7cfL,
      0x155a5a2687403bfL,
      0x2401a5178734d0bL,
      0xfc91687b31894b4fL,
      0x420b3f844e5e44aL,
      0x7b2869c396374ecL,
      0xf8437d4333f83d32L,
      0xff1a87f394ba971fL,
      0x3901a0d466899a2L,
      0x3cf9ccc225554f6L,
      0x14ed449ebdbed1dL,
      0xfa90ed630af36586L,
      0x60ef99504d6bfb8L,
      0xf945f7009a7a02e6L,
      0x101452ecf4aaec2L,
      0x8ebca315bccae9L,
      0x73035df93dedda0L,
      0x6a1845647247af1L,
      0x2b71b65b7d9202dL,
      0xf8d78929f9c051ceL,
      0x57397887c8f4c3dL,
      0xfe523128f9a7f8b5L
  )

  val random32s = Array[Long](
      0x3d7bca72L,
      0x6e125b10L,
      0xfffffffff3299127L,
      0xffffffffdefa0492L,
      0xffffffffeea0a2cfL,
      0xffffffffb8038179L,
      0xa0e5f0cL,
      0x4d830f21L,
      0xffffffffef18aa84L,
      0x4f042fc1L,
      0x12dbd8a5L,
      0xffffffffe3558dc1L,
      0x71ecbe93L,
      0xffffffffa956d5c6L,
      0x640c3bd5L,
      0x44d77fb1L,
      0x99c9f40L,
      0xffffffffe79d2c19L,
      0xffffffffee95932bL,
      0x6552cb0bL,
      0x5905b9f7L,
      0x56af0217L,
      0xffffffffd5f56c67L,
      0xffffffff839c8af1L,
      0x26d22d6bL,
      0x7cc75936L,
      0xffffffff9c62f33dL,
      0xffffffffa4268d06L,
      0x4c916c02L,
      0x751761a0L,
      0x5fc17458L,
      0xffffffffa5a6b220L,
      0x20ce206fL,
      0xffffffffc5b42c05L,
      0x45098ca1L,
      0x807263bL,
      0x2a8b38a0L,
      0xffffffffe5cd24adL,
      0x5e7f88c0L,
      0x510da3b0L,
      0x4ecc3adbL,
      0xffffffffd266abccL,
      0xffffffffe971b8e2L,
      0xffffffff865735a5L,
      0xffffffffcc6b4923L,
      0x67b55f19L,
      0xffffffff8aa1bcbcL,
      0xc292dc7L,
      0x71c176e0L,
      0xffffffffa1ebe8a1L,
      0x51280bd0L,
      0xffffffffe36f4136L,
      0xffffffff98cc54beL,
      0x631666d7L,
      0x5ed13defL,
      0x7a4a00c1L,
      0xffffffffdbc5d6d5L,
      0x31bbc1f0L,
      0xffffffff86e8d7feL,
      0x39102053L,
      0xffffffffbbd1f909L,
      0x76ae826dL,
      0x695da0fcL,
      0xffffffff8cd841acL,
      0xffffffffdfb7c5b3L,
      0xffffffffc4fbfcf8L,
      0x3467c343L,
      0x3fc83653L,
      0x47d1a280L,
      0x7681e774L,
      0x7a993e0dL,
      0xffffffff81c1eccaL,
      0x20bb0799L,
      0x7f9fab4fL,
      0x62c56b3aL,
      0xffffffff98d57d03L,
      0x6e000504L,
      0x7dfa0919L,
      0xffffffffdab49d4fL,
      0xfffffffffb630813L,
      0xffffffffeb753a49L,
      0x369ce15aL,
      0xffffffffa16519c1L,
      0xffffffffbd4e5f25L,
      0x75252458L,
      0xffffffff9030369fL,
      0xffffffffb1eaf374L,
      0x1951a5b0L,
      0x5d29e5a9L,
      0xffffffff83b2bcd6L,
      0x5914897dL,
      0x469b62ecL,
      0x1298eca5L,
      0x27d89dbL,
      0xffffffff9611ae2fL,
      0x3cb7b681L,
      0xffffffffbf181c44L,
      0xffffffffc079bb52L,
      0x1db7ba55L,
      0xfffffffffb8ba481L
  )

  val random28s = Array[Long](
      0x4bf407cL,
      0x3fe6d03L,
      0xfffffffffc395caaL,
      0x3e670b5L,
      0xfffffffffadb3094L,
      0x4a3854aL,
      0x27b0442L,
      0xfffffffffcc7de8aL,
      0xfffffffffad6ae63L,
      0xfffffffff919d007L,
      0xfffffffffa968f91L,
      0x3cfa4f3L,
      0x271b7e3L,
      0xfffffffff863b631L,
      0x2fc1dd1L,
      0xffffffffface82a0L,
      0x3a7f3a1L,
      0xfffffffff84f7f61L,
      0xfffffffffa80da12L,
      0xfffffffffa5054d2L,
      0xfffffffff840661aL,
      0xffffffffffaebe33L,
      0xffffffffff3b8d67L,
      0xfffffffffba4cdbcL,
      0xaadd77L,
      0xfffffffff9ee92bdL,
      0x3961a46L,
      0xd1e563L,
      0xfffffffffb56bff4L,
      0x39b0830L,
      0xfffffffff9b4dfb9L,
      0xfffffffffebbff11L,
      0x7fb8defL,
      0x7631b31L,
      0xffffffffff02a378L,
      0x58af86dL,
      0x136ac10L,
      0xfffffffffce326bbL,
      0x41d9d21L,
      0x6796221L,
      0x155a997L,
      0xfffffffffac50936L,
      0xfffffffff8050cf1L,
      0x7bbf6dL,
      0x68e77d4L,
      0x68c526dL,
      0x2a2cfdL,
      0x116c4a3L,
      0x6665ec2L,
      0x7d2e184L,
      0x11055efL,
      0xffffffffffd05f6cL,
      0xfffffffffba1eabaL,
      0x196d836L,
      0xfffffffffab9c352L,
      0x5990a80L,
      0x7b35ec7L,
      0x6a9a34bL,
      0xfffffffffb755173L,
      0x463948L,
      0xfffe16L,
      0xfffffffffdcdaa02L,
      0xfffffffff9b3b321L,
      0x3336e84L,
      0x7ace65aL,
      0x13f12f5L,
      0xffffffffff447470L,
      0xfffffffff9dc5f23L,
      0x3763734L,
      0xaf4ceeL,
      0x7a38222L,
      0x714426dL,
      0xfffffffff89ca133L,
      0xffffffffffd9f578L,
      0xfffffffff88b2ad5L,
      0x17deef5L,
      0xfffffffffed0aceeL,
      0xf6d7dL,
      0x53289aeL,
      0x7ca1cf3L,
      0x1086b23L,
      0xfffffffffd1ba4c6L,
      0x37eec82L,
      0x62bcef0L,
      0xfffffffffde9a50dL,
      0xfffffffff88d1293L,
      0x14cfffL,
      0xfffffffffcef8433L,
      0x1def34bL,
      0xfffffffffd625c0cL,
      0x52f2054L,
      0x744e0afL,
      0x45567caL,
      0xfffffffffd7fa0f9L,
      0xfffffffffb603ec4L,
      0xffffffffffc52c2cL,
      0x30c44ffL,
      0xffffffffff707c96L,
      0xfffffffffab1a802L,
      0xffffffffff407af5L
  )

  val random53s = Array[Long](
      0xa014b5eb2d544L,
      0xfff81ba21761c495L,
      0x4d77578d1370cL,
      0x49d5abe6d91c4L,
      0x6da66b7976ab8L,
      0xfff94d7c0aadc01dL,
      0x3ba7bb07a26c5L,
      0xfff4898488de1b39L,
      0x1fb4aa97c4e72L,
      0xfffc7d8417a681a2L,
      0xfff9ea38de4a30dcL,
      0xfff7cc020050b1d1L,
      0x4a8cf8ba7de6eL,
      0xecd5c1740b2e1L,
      0xffff1205454d9105L,
      0xfffa282763ece31aL,
      0xfff3307811507e49L,
      0xfff74422d262a9dbL,
      0xfff50edccd83516aL,
      0xdd1d46ac78c4aL,
      0x2abb05aee2182L,
      0xa59f192ca5caeL,
      0xfff50d7f12f2acb7L,
      0x93ad50a5325L,
      0xfff8a5df04621518L,
      0x7bf7bb28d7be4L,
      0x8a68176bafc25L,
      0x452e6fad6f308L,
      0xb6d883731cbd9L,
      0xd04102c60487dL,
      0x204932dbbe8dbL,
      0xd24a5d3f0743L,
      0xfff2a0c2b8ce989dL,
      0x1c43d2b5522f3L,
      0x3693351fb3fcbL,
      0xfff8d508357ce416L,
      0x14d3efb1e69a3L,
      0xfff513559b31387bL,
      0x4ac18f1c26f68L,
      0xe254405a2f781L,
      0xfffdc4202c90c060L,
      0xfda18b8021bb9L,
      0x4998d1faf5e5bL,
      0xfff6413227f51335L,
      0xfff709e7487f48f7L,
      0x4ecbb9e547e36L,
      0xfff45bf70302f287L,
      0xe1e2a5f71e8d9L,
      0xfff492b0260a9c91L,
      0x5a294db85a7caL,
      0xc64d9e7934795L,
      0xda57c677ae9ebL,
      0xac3008971e1eeL,
      0xfff8c1a9fd27b144L,
      0xfffc91854d903c4bL,
      0xfffba14683e5a47eL,
      0xfffd7569332f2758L,
      0x1985f2113ef89L,
      0xc8fb6f9fea5f0L,
      0xb578e10f39e0aL,
      0x98d38f04a793eL,
      0xe2788866f6470L,
      0x5ce6f05d120f0L,
      0xfffe99da13b495a3L,
      0xf6681aa1ae0bbL,
      0x5471e1bd27292L,
      0x58f35ef151e17L,
      0x1978fc992febcL,
      0x9bbaf76ee9d4eL,
      0x8b7d111577960L,
      0xfffbf414a7c7738eL,
      0xfff94951387b6dcaL,
      0xffff60f1a8f314eeL,
      0xfff31bc366eb66a2L,
      0x42e7ea6ed60e2L,
      0x9d39dbeb59809L,
      0xfff1fdb5df711dd7L,
      0x6d8a17d17b2c3L,
      0xa3b6df7859d59L,
      0xfc6f4a3206f89L,
      0xfff0b475160b6d34L,
      0x8ed5da55ea504L,
      0xfffbe0f14988afceL,
      0x5abb9a512cce7L,
      0xb7112b4631dfbL,
      0x1cdcdb10924f9L,
      0xce96c01207757L,
      0xfff6d01b8cc6ffd5L,
      0xa5e0d9f196b33L,
      0xfff65e6a8230c678L,
      0x1e0c8471c73f9L,
      0xfff0d63cba1187c0L,
      0x4c8c34775e0dfL,
      0xfffc32719be1253fL,
      0xdfdb18b3069eL,
      0xffff08ddb6521dceL,
      0xfffb8504baa56ed5L,
      0xfff636a8b0e10d9fL,
      0xfff53a3a57d7879dL,
      0xfffd04c9c2d2217cL
  )

  val random49s = Array[Long](
      0xffffaadec2d217e8L,
      0x26b0a76c736L,
      0xffff5c2795985e20L,
      0xffffcc86c84e340eL,
      0xffff4709d7192bc5L,
      0xffff51a731afe5a7L,
      0xf7c18b8f8cc5L,
      0xfffff82d0db13b05L,
      0x472e1ff5030L,
      0xffffb585f55db091L,
      0xba799238c077L,
      0xaca4bc25c803L,
      0xffffac7a05ba7a65L,
      0xe15c410618ceL,
      0x9d9382735d2L,
      0xffff9df99beaba86L,
      0xffff8bbb7c82d210L,
      0x7d03611cb0adL,
      0x5ddcdaae707L,
      0xbd5d44621db6L,
      0xffff9a88d7ce065fL,
      0x68f4a3e128fbL,
      0xe50d71a74fc3L,
      0xe59ddad271a7L,
      0xffff7bd085dd2a0aL,
      0xcf9ed3bb2fabL,
      0xd8d435860f1aL,
      0x2c2f17913b9aL,
      0x5e3a7dc1cf72L,
      0xbdbaffd3200fL,
      0xffff71713a91331dL,
      0xc28439f6afd6L,
      0xffffe4932c89e45aL,
      0xffff651e637c587cL,
      0xff8d6a7bdf52L,
      0xd5de021d7a1dL,
      0xffff487899dcad1fL,
      0xb3b65f1ac9afL,
      0xffff172337f799cfL,
      0xffff29278f4b09d3L,
      0xffff65230c0ec9f2L,
      0xfffffb7f459e3ef7L,
      0xffffc24a9a310341L,
      0x73fb969c308bL,
      0xffff4c2779ac4320L,
      0xeffa0cfcc2d6L,
      0xffffb8289101161bL,
      0xffff888a3e6f496dL,
      0x15d8e8f02ed4L,
      0xffffa862aa99a94fL,
      0xffff0605950d7ba9L,
      0xffff8f52d235f3e6L,
      0x2814f9ff142bL,
      0x86f491bc8e7L,
      0xffff7c20b1c7e3e0L,
      0xffff877ec2fedfa0L,
      0x1c9c90ef1b33L,
      0x7e96d2d53577L,
      0xd70c38e5e03dL,
      0xf4c7f83c5c12L,
      0x8aff40a4d2cL,
      0xffffea2834dc102eL,
      0xe24c95577550L,
      0xffff30b9a199bd84L,
      0xffff6ebf8a4b43a0L,
      0xa6f68ca2b1d0L,
      0xc09aa55f83f4L,
      0xffff396d59515e00L,
      0xffffa831e8063f29L,
      0x711a1d7afd97L,
      0xfd378e370ba1L,
      0xffffd4fc7e4af46bL,
      0x2b391cd72bcaL,
      0x9d780a909c5eL,
      0xcb1cfb7eef87L,
      0xc746d783ef07L,
      0xa924bd5a4666L,
      0x21b670f27e02L,
      0xffffc6436fd42240L,
      0xb2984ab77495L,
      0xfc8286e089bdL,
      0xffff1d6a6dd360c6L,
      0xfb5f894772f8L,
      0x58e5812dc46L,
      0xffff0d2e6466c29fL,
      0xffff590b25af6058L,
      0xffffe6c95ff7b2a2L,
      0x900d7aa3f0f9L,
      0xfffff19be035345fL,
      0x12583afc79afL,
      0xb64ffa7e6fb2L,
      0xffff63ecd3bdd5d8L,
      0xffff8224af2e4e4cL,
      0x3f67c3b24b23L,
      0x9a21efcb6c8aL,
      0x426341fce28cL,
      0xffff6474e962c354L,
      0x14d4d3e5a714L,
      0x836b8d03eef7L,
      0xffffef64ef32c6dcL
  )

  val random8s = Array[Long](
      0xffffffffffffff97L,
      0x31L,
      0xffffffffffffffa8L,
      0x26L,
      0xffffffffffffffd5L,
      0xcL,
      0x4bL,
      0x16L,
      0x35L,
      0x4L,
      0x62L,
      0xffffffffffffffd6L,
      0xffffffffffffffdfL,
      0x1aL,
      0x63L,
      0xffffffffffffffa2L,
      0xffffffffffffffc7L,
      0xffffffffffffffa2L,
      0xffffffffffffffdcL,
      0xffffffffffffff8aL,
      0x56L,
      0x16L,
      0xffffffffffffffb6L,
      0x66L,
      0x4aL,
      0x26L,
      0xffffffffffffffbdL,
      0x6L,
      0xffffffffffffffdaL,
      0x12L,
      0x5fL,
      0xffffffffffffffaeL,
      0x1cL,
      0x3cL,
      0xffffffffffffffb3L,
      0xffffffffffffffc1L,
      0xffffffffffffffabL,
      0xfffffffffffffff8L,
      0xffffffffffffffbdL,
      0xffffffffffffffbbL,
      0xffffffffffffff84L,
      0xffffffffffffffa6L,
      0xfffffffffffffff0L,
      0x64L,
      0x30L,
      0x3L,
      0xfffffffffffffff3L,
      0x42L,
      0x28L,
      0xffffffffffffffbbL,
      0xffffffffffffffa7L,
      0x4eL,
      0x74L,
      0x42L,
      0x69L,
      0xffffffffffffffebL,
      0xffffffffffffff94L,
      0xffffffffffffffe3L,
      0xffffffffffffff94L,
      0x13L,
      0xffffffffffffffa4L,
      0x18L,
      0x49L,
      0xffffffffffffffd3L,
      0xffffffffffffffbaL,
      0x1cL,
      0x72L,
      0xfffffffffffffff8L,
      0xffffffffffffff92L,
      0xffffffffffffffbfL,
      0x3L,
      0xaL,
      0x6dL,
      0x78L,
      0xffffffffffffff82L,
      0x79L,
      0x35L,
      0xffffffffffffffc0L,
      0xffffffffffffffc9L,
      0x16L,
      0xffffffffffffffe5L,
      0xffffffffffffff9dL,
      0xaL,
      0xffffffffffffffc4L,
      0x27L,
      0x7cL,
      0x14L,
      0x6eL,
      0x3eL,
      0x43L,
      0xffffffffffffffd4L,
      0x3bL,
      0x25L,
      0xffffffffffffffa6L,
      0x1dL,
      0xaL,
      0xfffffffffffffff8L,
      0xffffffffffffffbaL,
      0xffffffffffffffabL,
      0xfffffffffffffff6L
  )

  val randomPow2s = Array[Long](
      0x1000000000L,
      0x400000000000000L,
      0x2000000000000000L,
      0x10000000000L,
      0x200L,
      0x4000L,
      0x800000000000000L,
      0x2000000L,
      0x2000L,
      0x20000L,
      0x400000000000L,
      0x10000L,
      0x8000L,
      0x10000000000L,
      0x40000000000L,
      0x8000L,
      0x4000000000000000L,
      0x20000000000000L,
      0x200000000000000L,
      0x200000000000000L,
      0x2000000L,
      0x1000L,
      0x1L,
      0x400000L,
      0x10000000L,
      0x400L,
      0x20000000L,
      0x200000000L,
      0x8L,
      0x200L,
      0x80000000000L,
      0x20000000L,
      0x2000000L,
      0x100000000000L,
      0x4000000000L,
      0x10000000L,
      0x100L,
      0x200L,
      0x20000000000000L,
      0x40000000000L,
      0x80000000L,
      0x80000000000L,
      0x200000000L,
      0x20000000000000L,
      0x200000000000000L,
      0x8000000000000000L,
      0x400000L,
      0x1000L,
      0x8000000000000000L,
      0x4000000L,
      0x4000000000000000L,
      0x1L,
      0x8000000000L,
      0x40000000000000L,
      0x10000000000000L,
      0x4L,
      0x80000000000L,
      0x8000000L,
      0x200000000000000L,
      0x4L,
      0x40L,
      0x200L,
      0x800000000000L,
      0x800000L,
      0x4000000000000L,
      0x1000L,
      0x2000000000000000L,
      0x800000000000L,
      0x100000000000000L,
      0x8000000000000L,
      0x2000000000000L,
      0x80000000000000L,
      0x8000000000000000L,
      0x10000000000000L,
      0x100000000L,
      0x1000000L,
      0x1L,
      0x20L,
      0x80000000000000L,
      0x4000L,
      0x100000000L,
      0x100L,
      0x8L,
      0x80000000000000L,
      0x20L,
      0x4000000L,
      0x8000000000L,
      0x10L,
      0x400L,
      0x4000000000L,
      0x2000000L,
      0x800000000L,
      0x1L,
      0x2000000L,
      0x1000000000000000L,
      0x80000000L,
      0x8000L,
      0x2000000000L,
      0x100000000000L,
      0x40L
  )

  val randomConstantAdds = Array[LongToLongFun](
      num => num + -485L,
      num => num + 2166835052L,
      num => num + -1687483040L,
      num => num + -31658700139179L,
      num => num + -58620603170658703L,
      num => num + -1351517365766642L,
      num => num + 875131723604783L,
      num => num + -22604737732440L,
      num => num + 80213574L,
      num => num + -1422L,
      num => num + -463352117944L,
      num => num + -6049851806321961L,
      num => num + 177L,
      num => num + -1181665004423L,
      num => num + 1408992253L,
      num => num + -9942609L,
      num => num + 452805058512739L,
      num => num + 108599520105964L,
      num => num + 263775146743900L,
      num => num + -5468409731L,
      num => num + 1624065932039324L,
      num => num + -54115797507550668L,
      num => num + -865546668614390379L,
      num => num + -8141907733624193992L,
      num => num + -191533093819L,
      num => num + 13619773621L,
      num => num + -64255312753487343L,
      num => num + 3888833442066489L,
      num => num + -454223630L,
      num => num + -33836237650L,
      num => num + 14L,
      num => num + 32990110406590995L,
      num => num + -7993764562938L,
      num => num + 12L,
      num => num + 96586L,
      num => num + 4866L,
      num => num + -168251986L,
      num => num + 283L,
      num => num + -57959L,
      num => num + -685L,
      num => num + 1395671958798L,
      num => num + 586L,
      num => num + 460085361386743286L,
      num => num + 590679177997L,
      num => num + 45368L,
      num => num + 5926892624L,
      num => num + -3202578L,
      num => num + 15586L,
      num => num + 3157875L,
      num => num + 202162L,
      num => num + 20152199082L,
      num => num + 26L,
      num => num + 8547031721285087L,
      num => num + -8449947368329348L,
      num => num + -13789111214L,
      num => num + -327511654373050705L,
      num => num + 76190202L,
      num => num + 51808560221178341L,
      num => num + 97L,
      num => num + 31590602465417L,
      num => num + 50022213L,
      num => num + -110729L,
      num => num + 3547573086433L,
      num => num + 352492217261L,
      num => num + 6705362333532L,
      num => num + 856496134L,
      num => num + -24L,
      num => num + -13576298589315962L,
      num => num + 5189L,
      num => num + -725417L,
      num => num + 121337L,
      num => num + 41L,
      num => num + 386309L,
      num => num + -32396600662L,
      num => num + 12390770021L,
      num => num + 852419794146L,
      num => num + -232290L,
      num => num + 4497892L,
      num => num + 117L,
      num => num + 3490L,
      num => num + -175199L,
      num => num + -71340589094L,
      num => num + 1121L,
      num => num + -1214748919336L,
      num => num + 969L,
      num => num + -374877774915427L,
      num => num + 460997490L,
      num => num + 281027466181289L,
      num => num + 1920069772L,
      num => num + 15501512651L,
      num => num + -324636049354L,
      num => num + 11L,
      num => num + -270893481L,
      num => num + -10514L,
      num => num + 20455L,
      num => num + 20743870L,
      num => num + 2541L,
      num => num + 230334975160259755L,
      num => num + 154L,
      num => num + 55169841693L,
  )

  val randomSignedConstantDivs = Array[LongToLongFun](
      num => num / -485L,
      num => num / 2166835052L,
      num => num / -1687483040L,
      num => num / -31658700139179L,
      num => num / -58620603170658703L,
      num => num / -1351517365766642L,
      num => num / 875131723604783L,
      num => num / -22604737732440L,
      num => num / 80213574L,
      num => num / -1422L,
      num => num / -463352117944L,
      num => num / -6049851806321961L,
      num => num / 177L,
      num => num / -1181665004423L,
      num => num / 1408992253L,
      num => num / -9942609L,
      num => num / 452805058512739L,
      num => num / 108599520105964L,
      num => num / 263775146743900L,
      num => num / -5468409731L,
      num => num / 1624065932039324L,
      num => num / -54115797507550668L,
      num => num / -865546668614390379L,
      num => num / -8141907733624193992L,
      num => num / -191533093819L,
      num => num / 13619773621L,
      num => num / -64255312753487343L,
      num => num / 3888833442066489L,
      num => num / -454223630L,
      num => num / -33836237650L,
      num => num / 14L,
      num => num / 32990110406590995L,
      num => num / -7993764562938L,
      num => num / 12L,
      num => num / 96586L,
      num => num / 4866L,
      num => num / -168251986L,
      num => num / 283L,
      num => num / -57959L,
      num => num / -685L,
      num => num / 1395671958798L,
      num => num / 586L,
      num => num / 460085361386743286L,
      num => num / 590679177997L,
      num => num / 45368L,
      num => num / 5926892624L,
      num => num / -3202578L,
      num => num / 15586L,
      num => num / 3157875L,
      num => num / 202162L,
      num => num / 20152199082L,
      num => num / 26L,
      num => num / 8547031721285087L,
      num => num / -8449947368329348L,
      num => num / -13789111214L,
      num => num / -327511654373050705L,
      num => num / 76190202L,
      num => num / 51808560221178341L,
      num => num / 97L,
      num => num / 31590602465417L,
      num => num / 50022213L,
      num => num / -110729L,
      num => num / 3547573086433L,
      num => num / 352492217261L,
      num => num / 6705362333532L,
      num => num / 856496134L,
      num => num / -24L,
      num => num / -13576298589315962L,
      num => num / 5189L,
      num => num / -725417L,
      num => num / 121337L,
      num => num / 41L,
      num => num / 386309L,
      num => num / -32396600662L,
      num => num / 12390770021L,
      num => num / 852419794146L,
      num => num / -232290L,
      num => num / 4497892L,
      num => num / 117L,
      num => num / 3490L,
      num => num / -175199L,
      num => num / -71340589094L,
      num => num / 1121L,
      num => num / -1214748919336L,
      num => num / 969L,
      num => num / -374877774915427L,
      num => num / 460997490L,
      num => num / 281027466181289L,
      num => num / 1920069772L,
      num => num / 15501512651L,
      num => num / -324636049354L,
      num => num / 11L,
      num => num / -270893481L,
      num => num / -10514L,
      num => num / 20455L,
      num => num / 20743870L,
      num => num / 2541L,
      num => num / 230334975160259755L,
      num => num / 154L,
      num => num / 55169841693L,
  )

  val randomSignedConstantRems = Array[LongToLongFun](
      num => num % -485L,
      num => num % 2166835052L,
      num => num % -1687483040L,
      num => num % -31658700139179L,
      num => num % -58620603170658703L,
      num => num % -1351517365766642L,
      num => num % 875131723604783L,
      num => num % -22604737732440L,
      num => num % 80213574L,
      num => num % -1422L,
      num => num % -463352117944L,
      num => num % -6049851806321961L,
      num => num % 177L,
      num => num % -1181665004423L,
      num => num % 1408992253L,
      num => num % -9942609L,
      num => num % 452805058512739L,
      num => num % 108599520105964L,
      num => num % 263775146743900L,
      num => num % -5468409731L,
      num => num % 1624065932039324L,
      num => num % -54115797507550668L,
      num => num % -865546668614390379L,
      num => num % -8141907733624193992L,
      num => num % -191533093819L,
      num => num % 13619773621L,
      num => num % -64255312753487343L,
      num => num % 3888833442066489L,
      num => num % -454223630L,
      num => num % -33836237650L,
      num => num % 14L,
      num => num % 32990110406590995L,
      num => num % -7993764562938L,
      num => num % 12L,
      num => num % 96586L,
      num => num % 4866L,
      num => num % -168251986L,
      num => num % 283L,
      num => num % -57959L,
      num => num % -685L,
      num => num % 1395671958798L,
      num => num % 586L,
      num => num % 460085361386743286L,
      num => num % 590679177997L,
      num => num % 45368L,
      num => num % 5926892624L,
      num => num % -3202578L,
      num => num % 15586L,
      num => num % 3157875L,
      num => num % 202162L,
      num => num % 20152199082L,
      num => num % 26L,
      num => num % 8547031721285087L,
      num => num % -8449947368329348L,
      num => num % -13789111214L,
      num => num % -327511654373050705L,
      num => num % 76190202L,
      num => num % 51808560221178341L,
      num => num % 97L,
      num => num % 31590602465417L,
      num => num % 50022213L,
      num => num % -110729L,
      num => num % 3547573086433L,
      num => num % 352492217261L,
      num => num % 6705362333532L,
      num => num % 856496134L,
      num => num % -24L,
      num => num % -13576298589315962L,
      num => num % 5189L,
      num => num % -725417L,
      num => num % 121337L,
      num => num % 41L,
      num => num % 386309L,
      num => num % -32396600662L,
      num => num % 12390770021L,
      num => num % 852419794146L,
      num => num % -232290L,
      num => num % 4497892L,
      num => num % 117L,
      num => num % 3490L,
      num => num % -175199L,
      num => num % -71340589094L,
      num => num % 1121L,
      num => num % -1214748919336L,
      num => num % 969L,
      num => num % -374877774915427L,
      num => num % 460997490L,
      num => num % 281027466181289L,
      num => num % 1920069772L,
      num => num % 15501512651L,
      num => num % -324636049354L,
      num => num % 11L,
      num => num % -270893481L,
      num => num % -10514L,
      num => num % 20455L,
      num => num % 20743870L,
      num => num % 2541L,
      num => num % 230334975160259755L,
      num => num % 154L,
      num => num % 55169841693L,
  )

  val randomUnsignedConstantDivs = Array[LongToLongFun](
      num => java.lang.Long.divideUnsigned(num, 5L),
      num => java.lang.Long.divideUnsigned(num, 37489114602L),
      num => java.lang.Long.divideUnsigned(num, 2178767671L),
      num => java.lang.Long.divideUnsigned(num, 4040358699L),
      num => java.lang.Long.divideUnsigned(num, 13580L),
      num => java.lang.Long.divideUnsigned(num, 8061864801391L),
      num => java.lang.Long.divideUnsigned(num, 15314281L),
      num => java.lang.Long.divideUnsigned(num, 2443730L),
      num => java.lang.Long.divideUnsigned(num, 1219461553606L),
      num => java.lang.Long.divideUnsigned(num, 8004989789L),
      num => java.lang.Long.divideUnsigned(num, 1807978660835358727L),
      num => java.lang.Long.divideUnsigned(num, 880747465045L),
      num => java.lang.Long.divideUnsigned(num, 50341025270434213L),
      num => java.lang.Long.divideUnsigned(num, 15205349065L),
      num => java.lang.Long.divideUnsigned(num, 20069552311398L),
      num => java.lang.Long.divideUnsigned(num, 326904873916717L),
      num => java.lang.Long.divideUnsigned(num, 268769983867L),
      num => java.lang.Long.divideUnsigned(num, 27001806L),
      num => java.lang.Long.divideUnsigned(num, 1836L),
      num => java.lang.Long.divideUnsigned(num, 7348416599105459L),
      num => java.lang.Long.divideUnsigned(num, 979052559509L),
      num => java.lang.Long.divideUnsigned(num, 328906487891747L),
      num => java.lang.Long.divideUnsigned(num, 24865L),
      num => java.lang.Long.divideUnsigned(num, 518L),
      num => java.lang.Long.divideUnsigned(num, 33005636441900L),
      num => java.lang.Long.divideUnsigned(num, 2549612L),
      num => java.lang.Long.divideUnsigned(num, 36637209L),
      num => java.lang.Long.divideUnsigned(num, 7832715250093L),
      num => java.lang.Long.divideUnsigned(num, 540536449L),
      num => java.lang.Long.divideUnsigned(num, 228583214L),
      num => java.lang.Long.divideUnsigned(num, 1797236L),
      num => java.lang.Long.divideUnsigned(num, 7052508978L),
      num => java.lang.Long.divideUnsigned(num, 16356787272310256L),
      num => java.lang.Long.divideUnsigned(num, 622461352087L),
      num => java.lang.Long.divideUnsigned(num, 115816L),
      num => java.lang.Long.divideUnsigned(num, 11L),
      num => java.lang.Long.divideUnsigned(num, 1152L),
      num => java.lang.Long.divideUnsigned(num, 438L),
      num => java.lang.Long.divideUnsigned(num, 12L),
      num => java.lang.Long.divideUnsigned(num, 372613171622L),
      num => java.lang.Long.divideUnsigned(num, 33199847L),
      num => java.lang.Long.divideUnsigned(num, 598924540767L),
      num => java.lang.Long.divideUnsigned(num, 874977109482L),
      num => java.lang.Long.divideUnsigned(num, 16394290L),
      num => java.lang.Long.divideUnsigned(num, 35L),
      num => java.lang.Long.divideUnsigned(num, 351474L),
      num => java.lang.Long.divideUnsigned(num, 2824325793733505L),
      num => java.lang.Long.divideUnsigned(num, 86244135789L),
      num => java.lang.Long.divideUnsigned(num, 199958L),
      num => java.lang.Long.divideUnsigned(num, 560L),
      num => java.lang.Long.divideUnsigned(num, 1709659696855156234L),
      num => java.lang.Long.divideUnsigned(num, 972611200759667L),
      num => java.lang.Long.divideUnsigned(num, 190677308180571L),
      num => java.lang.Long.divideUnsigned(num, 247690L),
      num => java.lang.Long.divideUnsigned(num, 11219047L),
      num => java.lang.Long.divideUnsigned(num, 70494389998L),
      num => java.lang.Long.divideUnsigned(num, 12250106230186L),
      num => java.lang.Long.divideUnsigned(num, 7122L),
      num => java.lang.Long.divideUnsigned(num, 106366715949697560L),
      num => java.lang.Long.divideUnsigned(num, 4442810224543L),
      num => java.lang.Long.divideUnsigned(num, 1284310475097L),
      num => java.lang.Long.divideUnsigned(num, 531783937218L),
      num => java.lang.Long.divideUnsigned(num, 151911893L),
      num => java.lang.Long.divideUnsigned(num, 3280804045L),
      num => java.lang.Long.divideUnsigned(num, 9834L),
      num => java.lang.Long.divideUnsigned(num, 515648L),
      num => java.lang.Long.divideUnsigned(num, 1445254533L),
      num => java.lang.Long.divideUnsigned(num, 88118782757973L),
      num => java.lang.Long.divideUnsigned(num, 86749L),
      num => java.lang.Long.divideUnsigned(num, 17995135465L),
      num => java.lang.Long.divideUnsigned(num, 114328L),
      num => java.lang.Long.divideUnsigned(num, 6264066577L),
      num => java.lang.Long.divideUnsigned(num, 771862518556304L),
      num => java.lang.Long.divideUnsigned(num, 96240L),
      num => java.lang.Long.divideUnsigned(num, 9055872623178L),
      num => java.lang.Long.divideUnsigned(num, 2419976890842L),
      num => java.lang.Long.divideUnsigned(num, 1610101L),
      num => java.lang.Long.divideUnsigned(num, 2701605034L),
      num => java.lang.Long.divideUnsigned(num, 476752532037744L),
      num => java.lang.Long.divideUnsigned(num, 319331393952371711L),
      num => java.lang.Long.divideUnsigned(num, 126589189639L),
      num => java.lang.Long.divideUnsigned(num, 63009084840L),
      num => java.lang.Long.divideUnsigned(num, -8383530044789275485L),
      num => java.lang.Long.divideUnsigned(num, 260L),
      num => java.lang.Long.divideUnsigned(num, 28247123382L),
      num => java.lang.Long.divideUnsigned(num, 5657857819L),
      num => java.lang.Long.divideUnsigned(num, 1655502923763091726L),
      num => java.lang.Long.divideUnsigned(num, 57646808L),
      num => java.lang.Long.divideUnsigned(num, 9490324008029L),
      num => java.lang.Long.divideUnsigned(num, 46966L),
      num => java.lang.Long.divideUnsigned(num, 441322499727L),
      num => java.lang.Long.divideUnsigned(num, 8063574359511568L),
      num => java.lang.Long.divideUnsigned(num, 17272694641168269L),
      num => java.lang.Long.divideUnsigned(num, 632920L),
      num => java.lang.Long.divideUnsigned(num, 3321519L),
      num => java.lang.Long.divideUnsigned(num, 472L),
      num => java.lang.Long.divideUnsigned(num, 397361771086989L),
      num => java.lang.Long.divideUnsigned(num, 4197649L),
      num => java.lang.Long.divideUnsigned(num, 52238926513702L),
      num => java.lang.Long.divideUnsigned(num, 239019279220643L),
  )

  val randomUnsignedConstantRems = Array[LongToLongFun](
      num => java.lang.Long.remainderUnsigned(num, 5L),
      num => java.lang.Long.remainderUnsigned(num, 37489114602L),
      num => java.lang.Long.remainderUnsigned(num, 2178767671L),
      num => java.lang.Long.remainderUnsigned(num, 4040358699L),
      num => java.lang.Long.remainderUnsigned(num, 13580L),
      num => java.lang.Long.remainderUnsigned(num, 8061864801391L),
      num => java.lang.Long.remainderUnsigned(num, 15314281L),
      num => java.lang.Long.remainderUnsigned(num, 2443730L),
      num => java.lang.Long.remainderUnsigned(num, 1219461553606L),
      num => java.lang.Long.remainderUnsigned(num, 8004989789L),
      num => java.lang.Long.remainderUnsigned(num, 1807978660835358727L),
      num => java.lang.Long.remainderUnsigned(num, 880747465045L),
      num => java.lang.Long.remainderUnsigned(num, 50341025270434213L),
      num => java.lang.Long.remainderUnsigned(num, 15205349065L),
      num => java.lang.Long.remainderUnsigned(num, 20069552311398L),
      num => java.lang.Long.remainderUnsigned(num, 326904873916717L),
      num => java.lang.Long.remainderUnsigned(num, 268769983867L),
      num => java.lang.Long.remainderUnsigned(num, 27001806L),
      num => java.lang.Long.remainderUnsigned(num, 1836L),
      num => java.lang.Long.remainderUnsigned(num, 7348416599105459L),
      num => java.lang.Long.remainderUnsigned(num, 979052559509L),
      num => java.lang.Long.remainderUnsigned(num, 328906487891747L),
      num => java.lang.Long.remainderUnsigned(num, 24865L),
      num => java.lang.Long.remainderUnsigned(num, 518L),
      num => java.lang.Long.remainderUnsigned(num, 33005636441900L),
      num => java.lang.Long.remainderUnsigned(num, 2549612L),
      num => java.lang.Long.remainderUnsigned(num, 36637209L),
      num => java.lang.Long.remainderUnsigned(num, 7832715250093L),
      num => java.lang.Long.remainderUnsigned(num, 540536449L),
      num => java.lang.Long.remainderUnsigned(num, 228583214L),
      num => java.lang.Long.remainderUnsigned(num, 1797236L),
      num => java.lang.Long.remainderUnsigned(num, 7052508978L),
      num => java.lang.Long.remainderUnsigned(num, 16356787272310256L),
      num => java.lang.Long.remainderUnsigned(num, 622461352087L),
      num => java.lang.Long.remainderUnsigned(num, 115816L),
      num => java.lang.Long.remainderUnsigned(num, 11L),
      num => java.lang.Long.remainderUnsigned(num, 1152L),
      num => java.lang.Long.remainderUnsigned(num, 438L),
      num => java.lang.Long.remainderUnsigned(num, 12L),
      num => java.lang.Long.remainderUnsigned(num, 372613171622L),
      num => java.lang.Long.remainderUnsigned(num, 33199847L),
      num => java.lang.Long.remainderUnsigned(num, 598924540767L),
      num => java.lang.Long.remainderUnsigned(num, 874977109482L),
      num => java.lang.Long.remainderUnsigned(num, 16394290L),
      num => java.lang.Long.remainderUnsigned(num, 35L),
      num => java.lang.Long.remainderUnsigned(num, 351474L),
      num => java.lang.Long.remainderUnsigned(num, 2824325793733505L),
      num => java.lang.Long.remainderUnsigned(num, 86244135789L),
      num => java.lang.Long.remainderUnsigned(num, 199958L),
      num => java.lang.Long.remainderUnsigned(num, 560L),
      num => java.lang.Long.remainderUnsigned(num, 1709659696855156234L),
      num => java.lang.Long.remainderUnsigned(num, 972611200759667L),
      num => java.lang.Long.remainderUnsigned(num, 190677308180571L),
      num => java.lang.Long.remainderUnsigned(num, 247690L),
      num => java.lang.Long.remainderUnsigned(num, 11219047L),
      num => java.lang.Long.remainderUnsigned(num, 70494389998L),
      num => java.lang.Long.remainderUnsigned(num, 12250106230186L),
      num => java.lang.Long.remainderUnsigned(num, 7122L),
      num => java.lang.Long.remainderUnsigned(num, 106366715949697560L),
      num => java.lang.Long.remainderUnsigned(num, 4442810224543L),
      num => java.lang.Long.remainderUnsigned(num, 1284310475097L),
      num => java.lang.Long.remainderUnsigned(num, 531783937218L),
      num => java.lang.Long.remainderUnsigned(num, 151911893L),
      num => java.lang.Long.remainderUnsigned(num, 3280804045L),
      num => java.lang.Long.remainderUnsigned(num, 9834L),
      num => java.lang.Long.remainderUnsigned(num, 515648L),
      num => java.lang.Long.remainderUnsigned(num, 1445254533L),
      num => java.lang.Long.remainderUnsigned(num, 88118782757973L),
      num => java.lang.Long.remainderUnsigned(num, 86749L),
      num => java.lang.Long.remainderUnsigned(num, 17995135465L),
      num => java.lang.Long.remainderUnsigned(num, 114328L),
      num => java.lang.Long.remainderUnsigned(num, 6264066577L),
      num => java.lang.Long.remainderUnsigned(num, 771862518556304L),
      num => java.lang.Long.remainderUnsigned(num, 96240L),
      num => java.lang.Long.remainderUnsigned(num, 9055872623178L),
      num => java.lang.Long.remainderUnsigned(num, 2419976890842L),
      num => java.lang.Long.remainderUnsigned(num, 1610101L),
      num => java.lang.Long.remainderUnsigned(num, 2701605034L),
      num => java.lang.Long.remainderUnsigned(num, 476752532037744L),
      num => java.lang.Long.remainderUnsigned(num, 319331393952371711L),
      num => java.lang.Long.remainderUnsigned(num, 126589189639L),
      num => java.lang.Long.remainderUnsigned(num, 63009084840L),
      num => java.lang.Long.remainderUnsigned(num, -8383530044789275485L),
      num => java.lang.Long.remainderUnsigned(num, 260L),
      num => java.lang.Long.remainderUnsigned(num, 28247123382L),
      num => java.lang.Long.remainderUnsigned(num, 5657857819L),
      num => java.lang.Long.remainderUnsigned(num, 1655502923763091726L),
      num => java.lang.Long.remainderUnsigned(num, 57646808L),
      num => java.lang.Long.remainderUnsigned(num, 9490324008029L),
      num => java.lang.Long.remainderUnsigned(num, 46966L),
      num => java.lang.Long.remainderUnsigned(num, 441322499727L),
      num => java.lang.Long.remainderUnsigned(num, 8063574359511568L),
      num => java.lang.Long.remainderUnsigned(num, 17272694641168269L),
      num => java.lang.Long.remainderUnsigned(num, 632920L),
      num => java.lang.Long.remainderUnsigned(num, 3321519L),
      num => java.lang.Long.remainderUnsigned(num, 472L),
      num => java.lang.Long.remainderUnsigned(num, 397361771086989L),
      num => java.lang.Long.remainderUnsigned(num, 4197649L),
      num => java.lang.Long.remainderUnsigned(num, 52238926513702L),
      num => java.lang.Long.remainderUnsigned(num, 239019279220643L),
  )

  val randomSignedConstantDivsPow2 = Array[LongToLongFun](
      num => num / -1125899906842624L,
      num => num / 32L,
      num => num / 134217728L,
      num => num / 8L,
      num => num / 1024L,
      num => num / 70368744177664L,
      num => num / 128L,
      num => num / 549755813888L,
      num => num / 2251799813685248L,
      num => num / -18014398509481984L,
      num => num / 67108864L,
      num => num / 131072L,
      num => num / 128L,
      num => num / -8589934592L,
      num => num / -549755813888L,
      num => num / -18014398509481984L,
      num => num / 36028797018963968L,
      num => num / 536870912L,
      num => num / 9007199254740992L,
      num => num / -17179869184L,
      num => num / -8L,
      num => num / 2048L,
      num => num / 8589934592L,
      num => num / -4L,
      num => num / 274877906944L,
      num => num / 524288L,
      num => num / 2251799813685248L,
      num => num / 4611686018427387904L,
      num => num / -8L,
      num => num / -72057594037927936L,
      num => num / 72057594037927936L,
      num => num / -524288L,
      num => num / -32768L,
      num => num / -576460752303423488L,
      num => num / -1099511627776L,
      num => num / 2L,
      num => num / 67108864L,
      num => num / -67108864L,
      num => num / 549755813888L,
      num => num / 2097152L,
      num => num / 70368744177664L,
      num => num / -35184372088832L,
      num => num / -1152921504606846976L,
      num => num / 268435456L,
      num => num / -33554432L,
      num => num / -2251799813685248L,
      num => num / 70368744177664L,
      num => num / 8L,
      num => num / 33554432L,
      num => num / -562949953421312L,
      num => num / -4194304L,
      num => num / 262144L,
      num => num / 2251799813685248L,
      num => num / 4503599627370496L,
      num => num / 274877906944L,
      num => num / -8L,
      num => num / 1099511627776L,
      num => num / 9007199254740992L,
      num => num / -524288L,
      num => num / 8388608L,
      num => num / 65536L,
      num => num / 65536L,
      num => num / 2147483648L,
      num => num / 134217728L,
      num => num / -72057594037927936L,
      num => num / 2251799813685248L,
      num => num / 16777216L,
      num => num / 4194304L,
      num => num / -288230376151711744L,
      num => num / -33554432L,
      num => num / -2251799813685248L,
      num => num / 33554432L,
      num => num / -274877906944L,
      num => num / 2L,
      num => num / 1099511627776L,
      num => num / -536870912L,
      num => num / 8L,
      num => num / 128L,
      num => num / 70368744177664L,
      num => num / 576460752303423488L,
      num => num / -1099511627776L,
      num => num / -8L,
      num => num / 34359738368L,
      num => num / 35184372088832L,
      num => num / -1125899906842624L,
      num => num / -576460752303423488L,
      num => num / 9007199254740992L,
      num => num / -8589934592L,
      num => num / -17179869184L,
      num => num / -32L,
      num => num / 4096L,
      num => num / -9223372036854775808L,
      num => num / -9223372036854775808L,
      num => num / -64L,
      num => num / 4398046511104L,
      num => num / -1152921504606846976L,
      num => num / 1048576L,
      num => num / -36028797018963968L,
      num => num / 1125899906842624L,
      num => num / -17592186044416L,
  )

}

import LongMicroDataSets._

/**
 * Long micro-benchmarks.
 */
abstract class LongMicro extends org.scalajs.benchmark.Benchmark {
  import scala.util.Random

  @inline def doRun(randomAs: Array[Long], randomBs: Array[Long]): Long = {
    val alen = randomAs.length
    val blen = randomBs.length
    var result = 0L
    var i = 0
    while (i != alen) {
      var j = 0
      while (j != blen) {
        val a = randomAs(i)
        val b = randomBs(j)
        result ^= binaryOp(a, b)
        j += 1
      }
      i += 1
    }
    result
  }

  def binaryOp(a: Long, b: Long): Long
}

/**
 * Long micro-benchmarks for divisions by constants.
 */
abstract class LongConstantDivRemMicro extends org.scalajs.benchmark.Benchmark {
  @inline def doRun(randomAs: Array[LongToLongFun], randomBs: Array[Long]): Long = {
    val alen = randomAs.length
    val blen = randomBs.length
    var result = 0L
    var i = 0
    while (i != alen) {
      var j = 0
      while (j != blen) {
        val a = randomAs(i)
        val b = randomBs(j)
        result ^= a(b)
        j += 1
      }
      i += 1
    }
    result
  }

  @inline def doRunAndCheck(randomAs: Array[LongToLongFun], randomBs: Array[Long], expectedResult: Long): Unit = {
    val actual = doRun(randomAs, randomBs)
    if (actual != expectedResult)
      throw new Exception(s"expected $expectedResult but got $actual")
  }
}

object LongNop extends LongMicro {
  override def prefix = "LongNop"

  def run(): Unit = {
    if (doRun(random64s, random64s) != 0L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = a
}

object LongXor extends LongMicro {
  override def prefix = "LongXor"

  def run(): Unit = {
    if (doRun(random64s, random64s) != 0L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = a ^ b
}

object LongAdd extends LongMicro {
  override def prefix = "LongAdd"

  def run(): Unit = {
    if (doRun(random64s, random64s) != -3199834553443988620L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = a + b
}

object LongMul extends LongMicro {
  override def prefix = "LongMul"

  def run(): Unit = {
    if (doRun(random64s, random64s) != -4111379928290889828L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = a * b
}

object LongDiv32_32 extends LongMicro {
  override def prefix = "LongDiv32_32"

  def run(): Unit = {
    if (doRun(random32s, random32s) != 54L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = a / b
}

object LongDiv32_28 extends LongMicro {
  override def prefix = "LongDiv32_28"

  def run(): Unit = {
    if (doRun(random32s, random28s) != -1635L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = a / b
}

object LongDiv32_8 extends LongMicro {
  override def prefix = "LongDiv32_8"

  def run(): Unit = {
    if (doRun(random32s, random8s) != 396463647L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = a / b
}

object LongDiv53_53 extends LongMicro {
  override def prefix = "LongDiv53_53"

  def run(): Unit = {
    if (doRun(random53s, random53s) != 41L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = a / b
}

object LongDiv53_49 extends LongMicro {
  override def prefix = "LongDiv53_49"

  def run(): Unit = {
    if (doRun(random53s, random49s) != 516L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = a / b
}

object LongDiv53_8 extends LongMicro {
  override def prefix = "LongDiv53_8"

  def run(): Unit = {
    if (doRun(random53s, random8s) != 39173628864963L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = a / b
}

object LongDiv64_Pow2 extends LongMicro {
  override def prefix = "LongDiv64_Pow2"

  def run(): Unit = {
    if (doRun(random64s, randomPow2s) != 475661794007097238L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = a / b
}

object LongDiv64_64 extends LongMicro {
  override def prefix = "LongDiv64_64"

  def run(): Unit = {
    if (doRun(random64s, random64s) != 64L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = a / b
}

object LongDiv64_60 extends LongMicro {
  override def prefix = "LongDiv64_60"

  def run(): Unit = {
    if (doRun(random64s, random60s) != -404L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = a / b
}

object LongDiv64_8 extends LongMicro {
  override def prefix = "LongDiv64_8"

  def run(): Unit = {
    if (doRun(random64s, random8s) != 1415998624949685666L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = a / b
}

object LongConstantDivAddBaseline extends LongConstantDivRemMicro {
  override def prefix = "LongConstantDivAddBaseline"

  @noinline def run(): Unit =
    doRunAndCheck(randomConstantAdds, random64s, 2990845017735928704L)
}

object LongSignedConstantDiv extends LongConstantDivRemMicro {
  override def prefix = "LongSignedConstantDiv"

  @noinline def run(): Unit =
    doRunAndCheck(randomSignedConstantDivs, random64s, -88895587310485760L)
}

object LongSignedConstantRem extends LongConstantDivRemMicro {
  override def prefix = "LongSignedConstantRem"

  @noinline def run(): Unit =
    doRunAndCheck(randomSignedConstantRems, random64s, 7084982016537910592L)
}

object LongUnsignedConstantDiv extends LongConstantDivRemMicro {
  override def prefix = "LongUnsignedConstantDiv"

  @noinline def run(): Unit =
    doRunAndCheck(randomUnsignedConstantDivs, random64s, 3176689670430701472L)
}

object LongUnsignedConstantRem extends LongConstantDivRemMicro {
  override def prefix = "LongUnsignedConstantRem"

  @noinline def run(): Unit =
    doRunAndCheck(randomUnsignedConstantRems, random64s, -8387222896904660922L)
}

object LongSignedConstantDivPow2 extends LongConstantDivRemMicro {
  override def prefix = "LongSignedConstantDivPow2"

  @noinline def run(): Unit =
    doRunAndCheck(randomSignedConstantDivsPow2, random64s, -1598242140573774200L)
}

object LongToString32 extends LongMicro {
  override def prefix = "LongToString32"

  def run(): Unit = {
    if (doRun(random32s, random32s) != 0L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = java.lang.Long.toString(a).length()
}

object LongToString53 extends LongMicro {
  override def prefix = "LongToString53"

  def run(): Unit = {
    if (doRun(random53s, random53s) != 0L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = java.lang.Long.toString(a).length()
}

object LongToString64 extends LongMicro {
  override def prefix = "LongToString64"

  def run(): Unit = {
    if (doRun(random64s, random64s) != 0L)
      throw new Exception("wrong result")
  }

  @inline def binaryOp(a: Long, b: Long): Long = java.lang.Long.toString(a).length()
}
