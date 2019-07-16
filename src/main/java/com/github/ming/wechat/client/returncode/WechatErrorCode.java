package com.github.ming.wechat.client.returncode;

/**
 * 微信错误码
 *
 * @author : Liu Zeming
 * @date : 2018-12-14 02:05
 */
public enum WechatErrorCode {

    /**
     * 微信错误码以及对应错误码的含义
     */
    BusySystem(-1, "系统繁忙，此时请开发者稍候再试"),
    OK(0, "请求成功"),
    ObtainAccessTokenError(40001, "获取 access_token 时 AppSecret 错误，或者 access_token 无效。请开发者认真比对 AppSecret 的正确性，或查看是否正在为恰当的公众号调用接口"),
    IllegalCredenceType(40002, "不合法的凭证类型"),
    IllegalOpenID(40003, "不合法的 OpenID ，请开发者确认 OpenID （该用户）是否已关注公众号，或是否是其他公众号的 OpenID"),
    IllegalMediaType(40004, "不合法的媒体文件类型"),
    IllegalFileType(40005, "不合法的文件类型"),
    IllegalFileSize(40006, "不合法的文件大小"),
    IllegalMediaID(40007, "不合法的媒体文件 id"),
    IllegalMessageType(40008, "不合法的消息类型"),
    IllegalImageSize(40009, "不合法的图片文件大小"),
    IllegalAudioSize(40010, "不合法的语音文件大小"),
    IllegalVideoSize(40011, "不合法的视频文件大小"),
    IllegalMiniImageSize(40012, "不合法的缩略图文件大小"),
    IllegalAppID(40013, "不合法的 AppID ，请开发者检查 AppID 的正确性，避免异常字符，注意大小写"),
    IllegalAccessToken(40014, "不合法的 access_token ，请开发者认真比对 access_token 的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口"),
    IllegalMenuType(40015, "不合法的菜单类型"),
    IllegalButtonNum(40016, "不合法的按钮个数"),
    IllegalButtonNum2(40017, "不合法的按钮个数"),
    IllegalButtonName(40018, "不合法的按钮名字长度"),
    IllegalButtonKeyLength(40019, "不合法的按钮 KEY 长度"),
    IllegalButtonUrlLength(40020, "不合法的按钮 URL 长度"),
    IllegalMenuVersion(40021, "不合法的菜单版本号"),
    IllegalChildMenuSeries(40022, "不合法的子菜单级数"),
    IllegalChildMenuNum(40023, "不合法的子菜单按钮个数"),
    IllegalChildMenuType(40024, "不合法的子菜单按钮类型"),
    IllegalChildMenuNameLength(40025, "不合法的子菜单按钮名字长度"),
    IllegalChildMenuKeyLength(40026, "不合法的子菜单按钮 KEY 长度"),
    IllegalChildMenuUrlLength(40027, "不合法的子菜单按钮 URL 长度"),
    IllegalCustomUser(40028, "不合法的自定义菜单使用用户"),
    IllegalOauthCode(40029, "不合法的 oauth_code"),
    IllegalRefreshToken(40030, "不合法的 refresh_token"),
    IllegalOpenidList(40031, "不合法的 openid 列表"),
    IllegalOpenidLength(40032, "不合法的 openid 列表长度"),
    IllegalRequest(40033, "不合法的请求字符，不能包含 \\uxxxx 格式的字符"),
    IllegalParams(40035, "不合法的参数"),
    IllegalRequestFormat(40038, "不合法的请求格式"),
    IllegalUrlLength(40039, "不合法的 URL 长度"),
    IllegalGroupID(40050, "不合法的分组 id"),
    IllegalGroupName(40051, "分组名字不合法"),
    IllegalArtileIdx(40060, "删除单篇图文时，指定的 article_idx 不合法"),
    IllegalGroupName2(40117, "分组名字不合法"),
    IllegalMediaIDSize(40118, "media_id 大小不合法"),
    ButtonTypeError(40119, "button 类型错误"),
    ButtonTypeError2(40120, "button 类型错误"),
    IllegalMediaIDType(40121, "不合法的 media_id 类型"),
    IllegalWechatNum(40132, "微信号不合法"),
    ImageFormatNotSupport(40137, "不支持的图片格式"),
    DoNotAnotherWechatPublicUrl(40155, "请勿添加其他公众号的主页链接"),
    IPNotAllowed(40164, "调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置"),
    LackAccesstoken(41001, "缺少 access_token 参数"),
    LackAppid(41002, "缺少 appid 参数"),
    LackRefreshToken(41003, "缺少 refresh_token 参数"),
    LackSecret(41004, "缺少 secret 参数"),
    LackMediaData(41005, "缺少多媒体文件数据"),
    LackMediaIDParams(41006, "缺少 media_id 参数"),
    LackChildMenuData(41007, "缺少子菜单数据"),
    LackOauthCode(41008, "缺少 oauth code"),
    LackOpenid(41009, "缺少 openid"),
    AccessTokenOvertime(42001, "access_token 超时，请检查 access_token 的有效期，请参考基础支持 - 获取 access_token 中，对 access_token 的详细机制说明"),
    RefreshTokenOvertime(42002, "refresh_token 超时"),
    OauthCodeOvertime(42003, "oauth_code 超时"),
    UserNeedAuthAgain(42007, "用户修改微信密码， accesstoken 和 refreshtoken 失效，需要重新授权"),
    NeedGetRequest(43001, "需要 GET 请求"),
    NeedPostRequest(43002, "需要 POST 请求"),
    NeedHttpsRequest(43003, "需要 HTTPS 请求"),
    NeedReceiverSubscribe(43004, "需要接收者关注"),
    NeedFriend(43005, "需要好友关系"),
    NeedRemoveReceiverFromBlacklist(43019, "需要将接收者从黑名单中移除"),
    MediaFileNull(44001, "多媒体文件为空"),
    PostDataPackageNull(44002, "POST 的数据包为空"),
    ImageMessgeNull(44003, "图文消息内容为空"),
    MessageNull(44004, "文本消息内容为空"),
    MediaFileSizeToLarge(45001, "多媒体文件大小超过限制"),
    MessageSizeToLarge(45002, "消息内容超过限制"),
    TitleSizeToLarge(45003, "标题字段超过限制"),
    DescSizeToLarge(45004, "描述字段超过限制"),
    LinkSizeToLarge(45005, "链接字段超过限制"),
    ImageLinkSizeToLarge(45006, "图片链接字段超过限制"),
    AudioSizeToLarge(45007, "语音播放时间超过限制"),
    ImageMessageSizeToLarge(45008, "图文消息超过限制"),
    ApiCallLimit(45009, "接口调用超过限制"),
    MenuNumToLarge(45010, "创建菜单个数超过限制"),
    ApiCallTooBusy(45011, "API 调用太频繁，请稍候再试"),
    ReplyOvertime(45015, "回复时间超过限制"),
    SystemGroupDoNotUpdate(45016, "系统分组，不允许修改"),
    GroupNameToLang(45017, "分组名字过长"),
    GroupNumToLarge(45018, "分组数量超过上限"),
    CustomerServiceApiDownNumToLarge(45047, "客服接口下行条数超过上限"),
    MediaFileNotExist(46001, "不存在媒体数据"),
    MenuVersionNotExist(46002, "不存在的菜单版本"),
    MenuDataNotExist(46003, "不存在的菜单数据"),
    UserNotNotExist(46004, "不存在的用户"),
    JSONOrXmlWrong(47001, "解析 JSON/XML 内容错误"),
    ApiNotAuth(48001, "api 功能未授权，请确认公众号已获得该接口，可以在公众平台官网 - 开发者中心页中查看接口权限"),
    UserRefusedMessage(48002, "粉丝拒收消息（粉丝在公众号选项中，关闭了 “ 接收消息 ” ）"),
    ApiBanned(48004, "api 接口被封禁，请登录 mp.weixin.qq.com 查看详情"),
    ApiForbidDelete(48005, "api 禁止删除被自动回复和自定义菜单引用的素材"),
    ApiForbidClearCallNum(48006, "api 禁止清零调用次数，因为清零次数达到上限"),
    HaveNotAuthToSendMessage(48008, "没有该类型消息的发送权限"),
    UserHaveNotApiAuth(50001, "用户未授权该 api"),
    UserLimit(50002, "用户受限，可能是违规后接口被封禁"),
    ParamsError(61451, "参数错误 (invalid parameter)"),
    CustomerServiceInvalid(61452, "无效客服账号 (invalid kf_account)"),
    CustomerServiceAlreadyExist(61453, "客服帐号已存在 (kf_account exsited)"),
    CustomerServiceNameTolarge(61454, "客服帐号名长度超过限制 ( 仅允许 10 个英文字符，不包括 @ 及 @ 后的公众号的微信号 )(invalid kf_acount length)"),
    CustomerServiceNameError(61455, "客服帐号名包含非法字符 ( 仅允许英文 + 数字 )(illegal character in kf_account)"),
    CustomerServiceNumToLarge(61456, "客服帐号个数超过限制 (10 个客服账号 )(kf_account count exceeded)"),
    InvalidHeadImageType(61457, "无效头像文件类型 (invalid file type)"),
    SystemError(61450, "系统错误 (system error)"),
    DateFormatError(61500, "日期格式错误"),
    MenuIDNotExist(65301, "不存在此 menuid 对应的个性化菜单"),
    HaveNotUser(65302, "没有相应的用户"),
    HaveNotMenu(65303, "没有默认菜单，不能创建个性化菜单"),
    MatchRuleMessageNull(65304, "MatchRule 信息为空"),
    PersionMenuNumLimit(65305, "个性化菜单数量受限"),
    PersionMenuNotSupport(65306, "不支持个性化菜单的帐号"),
    PersionMenuMessageNull(65307, "个性化菜单信息为空"),
    ButtonNotResponse(65308, "包含没有响应类型的 button"),
    PersionMenuClose(65309, "个性化菜单开关处于关闭状态"),
    CountryNotNull(65310, "填写了省份或城市信息，国家信息不能为空"),
    ProvinceNotNull(65311, "填写了城市信息，省份信息不能为空"),
    CountryError(65312, "不合法的国家信息"),
    ProvinceError(65313, "不合法的省份信息"),
    CityError(65314, "不合法的城市信息"),
    MenuExternalLinksToMany(65316, "该公众号的菜单设置了过多的域名外跳（最多跳转到 3 个域名的链接）"),
    UrlError(65317, "不合法的 URL"),
    PostParamsError(9001001, "POST 数据参数不合法"),
    RemoteServiceError(9001002, "远端服务不可用"),
    TicketError(9001003, "Ticket 不合法"),
    PeripheryUserInfoError(9001004, "获取摇周边用户信息失败"),
    MerchantInfoError(9001005, "获取商户信息失败"),
    OpenIDError(9001006, "获取 OpenID 失败"),
    UploadFileError(9001007, "上传文件缺失"),
    UploadFileTypeError(9001008, "上传素材的文件类型不合法"),
    UploadFileSizeError(9001009, "上传素材的文件尺寸不合法"),
    UploadFault(9001010, "上传失败"),
    AccountError(9001020, "帐号不合法"),
    EquipmentError(9001021, "已有设备激活率低于 50% ，不能新增设备"),
    EquipmentApplyNumError(9001022, "设备申请数不合法，必须为大于 0 的数字"),
    EquipmentIdAlreadyExist(9001023, "已存在审核中的设备 ID 申请"),
    EquipmentIdNumToLarge(9001024, "一次查询设备 ID 数量不能超过 50"),
    EquipmentIdError(9001025, "设备 ID 不合法"),
    PageIdError(9001026, "页面 ID 不合法"),
    PageParamsError(9001027, "页面参数不合法"),
    PageIdDeleteError(9001028, "一次删除页面 ID 数量不能超过 10"),
    PageAlready(9001029, "页面已应用在设备中，请先解除应用关系再删除"),
    PageIdNumToLarge(9001030, "一次查询页面 ID 数量不能超过 50"),
    TimeZonesError(9001031, "时间区间不合法"),
    EquipmentAndPageError(9001032, "保存设备与页面的绑定关系参数错误"),
    StoreIdError(9001033, "门店 ID 不合法"),
    EquipmentRemarksToLarge(9001034, "设备备注信息过长"),
    EquipmentApplyParamsError(9001035, "设备申请参数不合法"),
    FindBeginError(9001036, "查询起始值 begin 不合法"),

    OpenIdNotBelong(49003, "传入的openid不属于此AppID"),

    UserTagIllegal(45157, "标签名非法，请注意不能和其他标签重名"),
    UserTagTooLong(45158, "标签名长度超过30个字节"),
    UserTagMistaken(45159, "非法的标签"),
    UserTagTooMany(45056, "创建的标签数过多，请注意不能超过100个"),
    UsetTagFansTooMany(45057, "该标签下粉丝数超过10w，不允许直接删除"),
    UserTagUpdateLimit(45058, "不能修改0/1/2这三个系统默认保留的标签"),
    UserTagFansExceed20(45059, "有粉丝身上的标签数已经超过限制，即超过20个"),

    ;

    private int code;

    private String msg;

    private WechatErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
