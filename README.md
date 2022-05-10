# CTS

OOP课程 咖喱铁路售票系统curry ticketing system（CTS） 作业记录

CTS-4

在CTS-3的基础上把买票设置为订单，可以对订单进行结算和取消，结算仅涉及对所有订单的一次性取消，需要注意的是如果钱不够结算的话需要状态回滚，不能消耗学生优惠次数，所以最好结算前先做一次检查，检查中不涉及对数据库的写操作即可。

订单取消实现方法其实类似于订单结算，先检查再进行对应操作。需要注意成功取消订单后不仅用户这里订单删除，火车那边的座位容量也要得到释放。

此外还涉及到了文件IO，实现方式在题目描述中给出，直接照搬即可。但是每次导入完要把阳性和阴性的人数累计（小坑点）。