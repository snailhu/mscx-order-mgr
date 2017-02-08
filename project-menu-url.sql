###########################################################################################################
#                                                 项目初始化                                                #
###########################################################################################################
INSERT INTO project (`id`, `moduleId`, `projectName`, `homeUrl`, `pCode`, `order`)
             VALUES ('PROJECT_10005', 'MODULE_00002', '交易管理', 'http://mscx-order-mgr.eastdc.cn:82', 'mscx-order-mgr', '203');

###########################################################################################################
#                                                 菜单初始化                                                #
###########################################################################################################
#########主菜单#########
insert into menu (`id`, `menuName`, `projectId`, `parent`, `path`, `order`, `dCreate`)
           values('MENU_00501','订单管理',	'PROJECT_10005',	 null,	'',	100,now());

insert into menu (`id`, `menuName`, `projectId`, `parent`, `path`, `order`, `dCreate`)
           values('MENU_00502','交易付款查询',	'PROJECT_10005',	 null,	'',	100,now());

#########子菜单#########
insert into menu (`id`, `menuName`, `projectId`, `parent`, `path`, `order`, `dCreate`)
           values('MENU_00503','订单查询',	'PROJECT_10005',	 'MENU_00501',  '/order/manageOrder.do'	,100,now());
insert into menu (`id`, `menuName`, `projectId`, `parent`, `path`, `order`, `dCreate`)
           values('MENU_00504','交易付款查询',	'PROJECT_10005',	 'MENU_00502',  '/paylog/showPayLog.do'	,100,now());

###########################################################################################################
#                                                 URL初始化                                                #
###########################################################################################################
#####共通Url#####
INSERT INTO project_url_rel (`id`, `pCode`, `url`, `urlName`, `desc`) 
			VALUES ('PURL_05001', 'mscx-order-mgr', '/user/currentUserInfo.json', '获取用户信息', '获取用户信息');
/*INSERT INTO project_url_rel (`id`, `pCode`, `url`, `urlName`, `desc`) 
			VALUES ('PURL_03001', 'mscx-app-mgr', '/user/currentUserInfo.json', '获取订单信息', '获取订单信息');
INSERT INTO project_url_rel (`id`, `pCode`, `url`, `urlName`, `desc`) 
			VALUES ('PURL_03002', 'mscx-app-mgr', '/applist.do', '获取微服务清单', '获取微服务清单');
INSERT INTO project_url_rel (`id`, `pCode`, `url`, `urlName`, `desc`) 
			VALUES ('PURL_03003', 'mscx-app-mgr', '/getAppDetail.do', '获取微服务详情', '获取微服务详情');*/

#####订单管理#####
insert into url values('URL_05001','MENU_00503','订单查询接口','/order/getOrderList.do','订单查询接口',now());
insert into url values('URL_05002','MENU_00503','付款登记接口','/order/payRecord.do','付款登记接口',now());
insert into url values('URL_05004','MENU_00503','付款登记接口','/order/manageOrder.do','付款登记接口',now());

#####交易记录查询#####
insert into url values('URL_05003','MENU_00504','交易记录查询接口','/paylog/getPayLogList.do','交易记录查询接口',now());
insert into url values('URL_05005','MENU_00504','交易记录查询接口','/paylog/showPayLog.do','交易记录查询接口',now());