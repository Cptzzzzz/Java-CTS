# CTS-2

采用的架构类似于web后端开发的架构，思路如下：

- 1.路由分发，通过对输入参数的第一项进行检查判断其应当由那一部分来处理。
- 2.逻辑处理，在路由分发后的函数中进行对应逻辑处理，其中用到对于数据的增删查改以及各种输入参数合法判断。
- 3.数据接口，对每种数据应提供CURD的接口，拓展提供高级CURD的用法。

为了简化，没有使用数据库，在类变量中使用AyyayList存储数据。

## 类说明

#### Test

作为整个程序的入口，主要负责路由分发任务，读取输入的第一个参数，根据参数对应的命令跳转至对应的处理函数，注意此时应当区别管理员模式和正常模式。

main函数中，先处理两种模式下通用的命令，在处理两种模式单独的命令，最后若还没处理命令则输出`Command does not exist`

#### User

处理用户部分的功能。

##### 实例变量:

|  变量名  |    属性类型    |       含义        |                        约束                        |
| :------: | :------------: | :---------------: | :------------------------------------------------: |
|   name   | private String |     用户姓名      |             包含26各字母大小写与下划线             |
|   sex    |  private char  |     用户性别      |               F/M/O：男性/女性/其他                |
|  region  |  private int   |  Aadhaar区域代码  |                取值范围[0001,1237]                 |
|   race   |  private int   |  Aadhaar种姓代码  |                取值范围[0020,0460]                 |
| identity |  private int   | Aadhaar生物识别码 | 前三位范围[000,100]，最后一位0/1/2：女性/男性/其他 |

##### 实例方法:

**提供每个属性与Aadhaar的getter and setter**

|  方法名  | 传入参数 | 返回值与属性  |         含义         |
| :------: | :------: | :-----------: | :------------------: |
| toString |    无    | public String | 打印用户的格式化信息 |

##### 类变量:

|  变量名   |        属性类型        |         含义         | 约束 |
| :-------: | :--------------------: | :------------------: | :--: |
| userArray | public ArrayList<User> | 包含所有已创建的用户 |  无  |

##### 类方法:

|        方法名        |        传入参数         |     返回值与属性      |                      含义                       |
| :------------------: | :---------------------: | :-------------------: | :---------------------------------------------: |
|    commandAddUser    |  String [] commandList  |  public static void   |             创建用户命令的处理函数              |
|      checkName       |       String name       | public static boolean |              检查用户姓名是否合法               |
|       checkSex       |       String sex        | public static boolean |              检查用户性别是否合法               |
|     checkAadhaar     | String Aadhaar,char sex | public static boolean |             检查用户Aadhaar是否合法             |
|  getRegionByAadhaar  |     String Aadhaar      |   public static int   |           通过Aadhaar卡号获取区域代码           |
|   getRaceByAadhaar   |     String Aadhaar      |   public static int   |           通过Aadhaar卡号获取种姓代码           |
| getIdentityByAadhaar |     String Aadhaar      |   public static int   |          通过Aadhaar卡号获取生物识别码          |
|     checkRegion      |       int region        | public static boolean |              检查区域代码是否合法               |
|      checkRace       |        int race         | public static boolean |              检查种姓代码是否合法               |
|    checkIdentity     |  int identity,char sex  | public static boolean | 检查生物识别码是否合法，包括检查与sex的对应关系 |
|  checkAadhaarExists  |     String Aadhaar      | public static boolean |             检查Aadhaar是否已被注册             |
|       addUser        |      User nowUser       |  public static void   |                    新增用户                     |

#### Station

##### 实例变量:

|  变量名  |    属性类型    |  含义  |    约束     |
| :------: | :------------: | :----: | :---------: |
|   name   | private String | 站点名 |     无      |
| distance |  private int   | 里程数 | 大于0的数字 |

##### 实例方法:

**提供每个属性的getter and setter**

| 方法名  |    传入参数     |  返回值与属性  |                             含义                             |
| :-----: | :-------------: | :------------: | :----------------------------------------------------------: |
| greater | Station Station | public boolean | 返回两个站点的比较结果，优先比较里程数大小，后比较站点的字典序，若this>Station则返回true，否则返回false |

##### 类变量:

无

##### 类方法:

无

#### Line

##### 实例变量:

|    变量名    |          属性类型          |      含义      |                   约束                   |
| :----------: | :------------------------: | :------------: | :--------------------------------------: |
|    number    |       private String       |    线路编号    |                    无                    |
|   capacity   |        private int         |    负载能力    |               大于0的数字                |
| stationArray | private ArrayList<Station> | 路线包含的站点 | 同一个路线站名不能重复，按里程数升序排列 |

##### 实例方法:

**提供number和capacity属性的getter and setter和stationArray的CURD**

|     方法名     |        传入参数         |  返回值与属性  |                             含义                             |
| :------------: | :---------------------: | :------------: | :----------------------------------------------------------: |
|    toString    |           无            | public String  |                     打印路线的格式化信息                     |
|   addStation   |   Station newStation    |  public void   |                      向线路中添加站点，                      |
|   delStation   |   String stationName    |  public void   |                  根据站点名从线路中删除站点                  |
|  checkStation  |   String stationName    | public boolean |          若路线中包含同名站点则返回false，否则true           |
|    greater     |        Line line        | public boolean | 返回两个线路的比较结果，比较线路的字典序，若this>Station则返回true，否则返回false |
| getTrainNumber |           无            |   public int   |                    返回当前线路的火车数量                    |
|     isFull     |           无            | public boolean |  返回当前线路是否满载，true代表满载，false代表还可容纳火车   |
|  getDistance   | String start,String end |   public int   |               返回当前路线上两个站点的里程数差               |

##### 类变量:

|  变量名   |           属性类型            |         含义         |            约束            |
| :-------: | :---------------------------: | :------------------: | :------------------------: |
| lineArray | public static ArrayList<Line> | 包含所有已创建的线路 | 按线路编号的字典序升序排序 |

##### 类方法:

|      方法名       |       传入参数        |     返回值与属性      |                        含义                         |
| :---------------: | :-------------------: | :-------------------: | :-------------------------------------------------: |
|  commandAddLine   | String [] commandList |  public static void   |               创建线路命令的处理函数                |
|  commandDelLine   | String [] commandList |  public static void   |               删除线路命令的处理函数                |
| commandAddStation | String [] commandList |  public static void   |               添加站点命令的处理函数                |
| commandDelStation | String [] commandList |  public static void   |               删除站点命令的处理函数                |
|  commandLineInfo  | String [] commandList |  public static void   |               查询线路命令的处理函数                |
|  commandListLIne  | String [] commandList |  public static void   |               列出线路信息的处理函数                |
|      addLine      |     Line newLine      |  public static void   |               新增路线，保证列表升序                |
|      delLine      |   String lineNumber   |  public static void   |     根据线路编号删除线路，同时删除其对应的列车      |
|     checkLine     |   String lineNumber   | public static boolean | 检查是否有同名线路，如果有则返回false，否则返回true |
|  getLineByNumber  |   String lineNumber   |  public static Line   |             返回对应线路编号的线路实例              |

#### Train

##### 实例变量:

|    变量名    |     属性类型      |       含义       |          约束           |
| :----------: | :---------------: | :--------------: | :---------------------: |
|    number    |  private String   |    列车车次号    | 1位车次代码+4位车次数字 |
|  lineNumber  |  private String   |  所属线路的编号  |    线路编号应当存在     |
| ticketPrice  | private double [] | 每公里票价的列表 | 详见对应子类的属性说明  |
| ticketNumber |  private int []   |   票张数的列表   | 详见对应子类的属性说明  |

##### 实例方法:

|       方法名        |   传入参数    |      返回值与属性      |                            含义                             |       说明       |
| :-----------------: | :-----------: | :--------------------: | :---------------------------------------------------------: | :--------------: |
|     checkTicket     |      无       | abstract public String |                 返回特定格式的字符串信息。                  | 需要在子类中实现 |
|      listTrain      |      无       | abstract public String |                 返回特定格式的字符串信息。                  | 需要在子类中实现 |
| getTicketIdBySeatId | String seatId |  abstract public int   | 根据座位号的字符串返回其在ticketPrice和ticketNumber中的索引 | 需要在子类中实现 |

##### 类变量:

无

##### 类方法:

父类的所有类方法在子类中均被重写，重写是其具体实现，父类方法调用子类重写的方法来完成对各类火车的操作

|         方法名         |       传入参数        |     返回值与属性      |                             含义                             |
| :--------------------: | :-------------------: | :-------------------: | :----------------------------------------------------------: |
|    commandAddTrain     | String [] commandList |  public static void   |      添加列车命令的处理函数,需要调用子类的同方法来实现       |
|    commandDelTrain     | String [] commandList |  public static void   |      删除列车命令的处理函数，需要调用子类的同方法来实现      |
|   commandCheckTicket   | String [] commandList |  public static void   |  查询火车票务信息命令的处理函数，需要调用子类的同方法来实现  |
|    commandListTrain    | String [] commandList |  public static void   |    列出火车信息命令的处理函数，需要调用子类的同方法来实现    |
|     listTrainInfo      |   String lineNumber   |   public static int   |   列出所有在此线路上火车的信息，需要调用子类的同方法来实现   |
|     listTrainInfo      |          无           |   public static int   | 列出所有火车的信息，需要调用子类的同方法来实现，与上一个方法互为重载 |
|       countTrain       |          无           |   public static int   |      返回现有的所有火车数量，需要调用子类的同方法来实现      |
| countTrainByLineNumber |   String lineNumber   |   public static int   |   返回所有在此线路上的火车数量，需要调用子类的同方法来实现   |
|  delTrainByLineNumber  |   String lineNumber   |   public static int   | 根据线路号删除所有在此线路上的火车，需要调用子类的同方法来实现 |
|      checkNumber       |     String number     | public static boolean | 检查列车车次后四位是否符合规范，符合规范返回true，不符合返回false |
|  getTrainTypeByNumber  |  String trainNumber   |   public static int   | 根据列车名返回其类型，1：普通车，2：Gatimaan，3：Koya，-1：非法列车名 |

#### NormalTrain

##### 实例变量:

|    变量名    |     属性类型      |       含义       |                   约束                    |
| :----------: | :---------------: | :--------------: | :---------------------------------------: |
|    number    |  private String   |    列车车次号    |               0+4位车次数字               |
|  lineNumber  |  private String   |  所属线路的编号  |             线路编号应当存在              |
| ticketPrice  | private double [] | 每公里票价的列表 | 列表长度为3，分别代表坐票、站票、挂票价格 |
| ticketNumber |  private int []   |   票张数的列表   | 列表长度为3，分别代表坐票、站票、挂票数量 |

##### 实例方法:

**提供各个属性的getter和setter**

|       方法名        |        传入参数         |  返回值与属性  |                             含义                             |         备注         |
| :-----------------: | :---------------------: | :------------: | :----------------------------------------------------------: | :------------------: |
|     checkTicket     |           无            | public String  |                  返回特定格式的字符串信息。                  | 对父类抽象方法的实现 |
|      listTrain      |           无            | public String  |                  返回特定格式的字符串信息。                  | 对父类抽象方法的实现 |
| getTicketIdBySeatId |      String seatId      |   public int   | 根据座位号的字符串返回其在ticketPrice和ticketNumber中的索引  | 对父类抽象方法的实现 |
|       greater       | NormalTrain normalTrain | public boolean | 返回this与normalTrain的字典序比较结果，this>normalTrain则返回true，否则返回false |  普通火车的比较方法  |

##### 类变量:

|   变量名   |           属性类型            |        含义         |            约束            |
| :--------: | :---------------------------: | :-----------------: | :------------------------: |
| trainArray | public ArrayList<NormalTrain> | 包含所有NormalTrain | 按列车车次的字典序升序排序 |

##### 类方法:

|         方法名         |         传入参数          |       返回值与属性        |                             含义                             |
| :--------------------: | :-----------------------: | :-----------------------: | :----------------------------------------------------------: |
|    commandAddTrain     |   String [] commandList   |    public static void     |                  添加普通列车命令的处理函数                  |
|    commandDelTrain     |   String [] commandList   |    public static void     |                  删除普通列车命令的处理函数                  |
|   commandCheckTicket   |   String [] commandList   |    public static void     |               查询普通火车的票务信息的处理函数               |
|       checkTrain       |       String number       |   public static boolean   |        检查是否有同名火车，有返回false，没有返回true         |
|     addNormalTrain     |  NormalTrain normalTrain  |    public static void     | 向normalTrainArray中添加新的NormalTrain，同时保证normalTrainArray中火车按字典序升序 |
|     delNormalTrain     |       String number       |    public static void     |                      根据列车号删除列车                      |
| getNormalTrainByNumber |       String number       | public static NormalTrain |                 根据列车号查询并返回对应列车                 |
|      checkSeatId       |       String seatId       |   public static boolean   | 检查席位代号与火车类型是否对应，如果对应返回true，否则返回false |
|     listTrainInfo      |          int tot          |     public static int     |            根据序号打印火车信息，tot是火车的序号             |
|     listTrainInfo      | String lineNumber,int tot |     public static int     |  根据序号和线路号打印在此线路上的火车信息，tot是火车的序号   |
|       countTrain       |            无             |     public static int     |                    返回当前普通火车的数量                    |
| countTrainByLineNumber |     String lineNumber     |     public static int     |                  返回在对应线路上的火车数量                  |
|  delTrainByLineNumber  |     String lineNumber     |    public static void     |                    删除在对应路线上的火车                    |

#### GatimaanTrain

类似于NormalTrain

#### KoyaTrain

类似于NormalTrain

