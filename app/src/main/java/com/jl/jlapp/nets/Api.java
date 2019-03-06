package com.jl.jlapp.nets;


import com.jl.jlapp.eneity.ActivityInformationByIdModel;
import com.jl.jlapp.eneity.ActivityMessageByUserIdModel;
import com.jl.jlapp.eneity.ActivitysAndCouponsByGoodsMsgModel;
import com.jl.jlapp.eneity.AllAvailableCouponModel;
import com.jl.jlapp.eneity.AuthTask;
import com.jl.jlapp.eneity.CommitVatInvoiceAptitudeToCheckModel;
import com.jl.jlapp.eneity.CopyOrderShoppingCartModel;
import com.jl.jlapp.eneity.CouponMsgModel;
import com.jl.jlapp.eneity.DecisionInventoryResultModel;
import com.jl.jlapp.eneity.EffectPreSellActivityInformationModel;
import com.jl.jlapp.eneity.EvaluationDetailByUserIdAndIdModel;
import com.jl.jlapp.eneity.EvaluationSubmitResultModel;
import com.jl.jlapp.eneity.GeneralInvoiceModel;
import com.jl.jlapp.eneity.GoodsBrand;
import com.jl.jlapp.eneity.InsertOrderResultModel;
import com.jl.jlapp.eneity.MessageNoReadNumModel;
import com.jl.jlapp.eneity.OrderEvaluationDetailModel;
import com.jl.jlapp.eneity.OrderListModel;
import com.jl.jlapp.eneity.OrderMessageListModel;
import com.jl.jlapp.eneity.OrderPayResult;
import com.jl.jlapp.eneity.OrderTable;
import com.jl.jlapp.eneity.AdvertisementByIdModel;
import com.jl.jlapp.eneity.ApplyCustomerServiceModel;
import com.jl.jlapp.eneity.CancleOrderModel;
import com.jl.jlapp.eneity.ClassificationModel;
import com.jl.jlapp.eneity.CustomerServiceByUserIdAndOrderIdModel;
import com.jl.jlapp.eneity.EffectAdvertisementByTypeModel;
import com.jl.jlapp.eneity.GoodsDetailModel;
import com.jl.jlapp.eneity.GoodsListModel;
import com.jl.jlapp.eneity.InvoiceUnitAndVatInvoiceAptitude;
import com.jl.jlapp.eneity.ImgUploadModel;
import com.jl.jlapp.eneity.LoginModel;
import com.jl.jlapp.eneity.MessageCodeModel;
import com.jl.jlapp.eneity.News_ListModel;
import com.jl.jlapp.eneity.OrderDetail;
import com.jl.jlapp.eneity.OrderStateDetailModel;
import com.jl.jlapp.eneity.HotSearchWorld;
import com.jl.jlapp.eneity.ParamForGetActivitysAndCouponsByGoodsMsg;
import com.jl.jlapp.eneity.PostModel;
import com.jl.jlapp.eneity.PostageModel;
import com.jl.jlapp.eneity.PresellMsg;
import com.jl.jlapp.eneity.ShipAddressModel;
import com.jl.jlapp.eneity.ShoppingCartModel;
import com.jl.jlapp.eneity.UserCouponInfosModel;
import com.jl.jlapp.eneity.VatInvoiceAptitudeByUserIdModel;
import com.jl.jlapp.eneity.VersionMessageModel;
import com.jl.jlapp.eneity.WXAccessTokenEntity;
import com.jl.jlapp.eneity.WXOrderInfo;
import com.jl.jlapp.eneity.WXUserInfo;
/*import com.jl.jlapp.eneity.WXAccessTokenEntity;
import com.jl.jlapp.eneity.WXUserInfo;*/

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by fyf on 2018/1/13.
 */

public interface Api {

    //获取商品列表
    @GET("goodsInformation/getGoodsList")
    Flowable<GoodsListModel> getGoodsList(@Query(AppFinal.Nets.P_SORTTYPE) int sortType,
                                          @Query(AppFinal.Nets.P_PRICESORT) String priceSort,
                                          @Query(AppFinal.Nets.P_SEARCHNAME) String searchName,
                                          @Query(AppFinal.Nets.P_ISHASGOODS) String isHasGoods,
                                          @Query(AppFinal.Nets.P_MINPRICE) Double minPrice,
                                          @Query(AppFinal.Nets.P_MAXPRICE) Double maxPrice,
                                          @Query(AppFinal.Nets.P_BRANDNAME) String brandName,
                                          @Query(AppFinal.Nets.P_CLASSIFICATIONID) int classificationId);

    //获取商品详情
    @GET("goodsInformation/getGoodsDetailMsgByGoodsId")
    Flowable<GoodsDetailModel> getGoodsDetailMsgByGoodsId(@Query("goodsId") int goodsId);

    //获取热门搜索词
    @GET("goodsInformation/selectHotSearchWord")
    Flowable<HotSearchWorld> selectHotSearchWord();

    //获取一二级分类信息
    @GET("classification/getClassificationMsg")
    Flowable<ClassificationModel> getClassificationMsg();

    //获取订单状态详情
    @GET("order/ getOrderStateDetail")
    Flowable<OrderStateDetailModel> getOrderStateDetail(@Query("orderId") int orderId,
                                                        @Query("userId") int userId);

    //根据广告类型获取正在生效的广告
    @GET("advertisementInformation/getEffectAdvertisementByType")
    Flowable<EffectAdvertisementByTypeModel> getEffectAdvertisementByType(@Query("type") int type);

    //根据广告id获取广告信息
    @GET("advertisementInformation/getAdvertisementById")
    Flowable<AdvertisementByIdModel> getAdvertisementById(@Query("advertisementInformationId") int advertisementInformationId);


    //获取首页新品上架展示所需数据
    @GET("advertisementInformation/getNews_List")
    Flowable<News_ListModel> getNews_List();

    //获取商品列表
    @GET("goodsInformation/getGoodsList_Ameliorate")
    Flowable<GoodsListModel> getGoodsListAmeliorate(@Query(AppFinal.Nets.P_SORTTYPE) int sortType,
                                                    @Query(AppFinal.Nets.P_PRICESORT) String priceSort,
                                                    @Query(AppFinal.Nets.P_SEARCHNAME) String searchName,
                                                    @Query(AppFinal.Nets.P_ISHASGOODS) String isHasGoods,
                                                    @Query(AppFinal.Nets.P_MINPRICE) Double minPrice,
                                                    @Query(AppFinal.Nets.P_MAXPRICE) Double maxPrice,
                                                    @Query(AppFinal.Nets.P_BRANDNAME) String brandName,
                                                    @Query(AppFinal.Nets.P_CLASSIFICATIONID) int classificationId);

    //根据订单id和用户id获取售后详情
    @GET("customerService/getCustomerServiceByUserIdAndOrderId")
    Flowable<CustomerServiceByUserIdAndOrderIdModel> getCustomerServiceByUserIdAndOrderId(@Query("orderId") int orderId,
                                                                                          @Query("userId") int userId);

    //获取收货人地址信息
    @GET("shippingAddress/getShippingAddressByUserId")
    Flowable<ShipAddressModel> getShippingAddressByUserId(@Query("userId") int userId);

    //获取用户的购物车信息
    @GET("shoppingCart/getShoppingCart")
    Flowable<ShoppingCartModel> getShoppingCart(@Query("userId") int userId);


    /**
     * 购物车的编辑事件/(在购物车中+/-数量)
     *
     * @param id
     * @param userId
     * @param operation
     * @param goodsSpecificationDetailsId
     * @param goodsNum
     * @return
     */
    @FormUrlEncoded
    @POST("shoppingCart/editShoppingCartGoodsById")
    Flowable<PostModel> editShoppingCartGoodsById(@Field("id") int id,
                                                  @Field("userId") int userId,
                                                  @Field("operation") int operation,
                                                  @Field("goodsSpecificationDetailsId") int goodsSpecificationDetailsId,
                                                  @Field("goodsNum") int goodsNum);

    /**
     * 删除购物车里的信息(批量)
     *
     * @param userId 用户id
     * @param ids    当前需要删除的信息的主键id数组(购物车表的主键id)
     * @return
     */
    @FormUrlEncoded
    @POST("shoppingCart/deleteBatchShoppingCartById")
    Flowable<PostModel> deleteBatchShoppingCartById(@Field("userId") int userId, @Field("ids") int[] ids);

    /**
     * 删除购物车里的信息(单个)
     *
     * @param userId 用户id
     * @param id     当前需要删除的信息的主键id(购物车表的主键id)
     * @return
     */
    @FormUrlEncoded
    @POST("shoppingCart/deleteShoppingCartById")
    Flowable<PostModel> deleteShoppingCartById(@Field("userId") int userId, @Field("id") int id);

    /**
     * 添加购物车
     *
     * @param userId                      用户id
     * @param goodsDetailsId              商品id
     * @param goodsSpecificationDetailsId 商品详情id
     * @param goodsNum                    购买数量
     * @return
     */
    @FormUrlEncoded
    @POST("shoppingCart/insertShoppingCart")
    Flowable<PostModel> insertShoppingCart(@Field("userId") int userId, @Field("goodsDetailsId") int goodsDetailsId,
                                           @Field("goodsSpecificationDetailsId") int goodsSpecificationDetailsId, @Field("goodsNum") int goodsNum,
                                           @Field("activityId") int activityId, @Field("activityName") String activityName);

    /*//获取一二级分类信息
    @GET("list.json")
    Flowable<ClassificationModel> getLocal();*/

    //登录时获取验证码
    @GET("user/MessageCode")
    Flowable<MessageCodeModel> getMessageCode(@Query("mobile") String mobile);

    //用户登录
    @FormUrlEncoded
    @POST("user/login")
    Flowable<LoginModel> login(@Field("account") String account, @Field("type") int type);

    //用户绑定手机号  type:0:手机号码    1:微信    2:QQ    3:支付宝
    @FormUrlEncoded
    @POST("user/bindPhone")
    Flowable<LoginModel> bindPhone(@Field("phone") String phone,
                                   @Field("account") String account,
                                   @Field("type") int type);

    //根据订单id和用户id获取订单详情详情
    @GET("order/getOrderDetail")
    Flowable<OrderDetail> getOrderDetail(@Query("orderId") int orderId,
                                         @Query("userId") int userId);

    //再次购买
    @POST("shoppingCart/copyorderShoppingCart")
    Flowable<CopyOrderShoppingCartModel> buyAgain(@Query("orderId") int orderId,
                                                  @Query("userId") int userId);

    //根据用户id获取该用户的发票抬头单位信息以及增票信息
    @GET("invoice/getInvoiceUnitAndVatInvoiceAptitudeByUserId")
    Flowable<InvoiceUnitAndVatInvoiceAptitude> getInvoiceUnitAndVatInvoiceAptitudeByUserId(@Query("userId") int userId);

    //根据用户id添加发票抬头单位（普票）信息
    @FormUrlEncoded
    @POST("invoice/addInvoiceUnit")
    Flowable<PostModel> addInvoiceUnit(@Field("unitName") String unitName, @Field("taxpayerIdentificationNumber") String taxpayerIdentificationNumber, @Field("userId") int userId);

    //根据商品信息列表获取活动列表和优惠券列表
    @POST("activityInformation/getActivitysAndCouponsByGoodsMsg")
    Flowable<ActivitysAndCouponsByGoodsMsgModel> getActivitysAndCouponsByGoodsMsg(@Body ParamForGetActivitysAndCouponsByGoodsMsg param);

    //生成订单时获取邮费
    @GET("freeDelivery/getPostage")
    Flowable<PostageModel> getPostage(@Query("provinceCode") String provinceCode, @Query("cityCode") String cityCode, @Query("countyCode") String countyCode,
                                      @Query("ringCode") String ringCode, @Query("orderMoney") double orderMoney);

    //根据用户id获取该用户拥有的所有优惠券信息
    @GET("couponInformation/getCouponInfoByUserId")
    Flowable<UserCouponInfosModel> getCouponInfoByUserId(@Query("userId") int userId);

    //提交订单
    @POST("order/insertOrder")
    Flowable<InsertOrderResultModel> insertOrder(@Body OrderTable orderTable);

    //提交订单前判断商品是否有库存
    @POST("order/decisionInventory")
    Flowable<DecisionInventoryResultModel> decisionInventory(@Body OrderTable orderTable);

    //用户提交售后申请
    @FormUrlEncoded
    @POST("customerService/applyCustomerService")
    Flowable<ApplyCustomerServiceModel> applyCustomerService(@Field("serviceType") int serviceType,
                                                             @Field("userId") int userId,
                                                             @Field("orderId") int orderId,
                                                             @Field("problemDescription") String problemDescription,
                                                             @Field("name") String name,
                                                             @Field("phone") String phone,
                                                             @Field("files") List<String> files);

    //图片上传
    @Multipart
    @POST("FileUpload/imgUpload")
    Flowable<ImgUploadModel> uploadPhoto(@Part MultipartBody.Part[] file);

    //用户评价
    @FormUrlEncoded
    @POST("goodsEvaluation/insertGoodsEvaluation")
    Flowable<EvaluationSubmitResultModel> commitEvaluation(@Field("orderDetailId") int orderDetailId,
                                                           @Field("userId") int userId,
                                                           @Field("evaluationContent") String evaluationContent,
                                                           @Field("score") int score,
                                                           @Field("files") List<String> files);

    //评价详情
    @GET("goodsEvaluation/getEvaluationDetailByUserIdAndId")
    Flowable<EvaluationDetailByUserIdAndIdModel> getEvaluationDetailByUserIdAndId(@Query("id") int id, @Query("userId") int userId);


    //用户操作订单
    @FormUrlEncoded
    @POST("order/cancleOrder")
    Flowable<CancleOrderModel> cancleOrder(@Field("orderId") int orderId,
                                           @Field("userId") int userId,
                                           @Field("operation") int operation);

    //获取用户的订单列表
    @GET("order/getOrderList")
    Flowable<OrderListModel> getOrderList(@Query("userId") int userId);

    //根据订单id和用户id获取订单需要评价的订单详情
    @GET("order/getOrderDetailNoEvaluation")
    Flowable<OrderDetail> getOrderDetailNoEvaluation(@Query("orderId") int orderId,
                                                     @Query("userId") int userId);

    //根据订单id获取订单评价详情
    @GET("goodsEvaluation/getEvaluationDetailByUserIdAndOrderId")
    Flowable<OrderEvaluationDetailModel> getEvaluationDetailByUserIdAndOrderId(@Query("orderId") int orderId,
                                                                               @Query("userId") int userId);


    //添加线下付款信息
    @FormUrlEncoded
    @POST("offlinePayment/insertOfflinePayment")
    Flowable<PostModel> insertOfflinePayment(@Field("orderNo") String orderNo,
                                             @Field("userId") int userId,
                                             @Field("remitterName") String remitterName,
                                             @Field("remitterAccount") String remitterAccount,
                                             @Field("payeeName") String payeeName,
                                             @Field("payeeAccount") String payeeAccount,
                                             @Field("payeeAccountDepositBank") String payeeAccountDepositBank,
                                             @Field("remittanceAmount") double remittanceAmount,
                                             @Field("files") List<String> files);

    //根据用户id获取增票资质信息
    @GET(" invoice/getVatInvoiceAptitudeByUserId")
    Flowable<VatInvoiceAptitudeByUserIdModel> getVatInvoiceAptitudeByUserId(@Query("userId") int userId);

    //用户提交增票资质
    @FormUrlEncoded
    @POST("invoice/commitVatInvoiceAptitudeToCheck")
    Flowable<CommitVatInvoiceAptitudeToCheckModel> commitVatInvoiceAptitudeToCheck(@Field("userId") int userId,
                                                                                   @Field("unitName") String unitName,
                                                                                   @Field("taxpayerIdentificationNumber") String taxpayerIdentificationNumber ,
                                                                                   @Field("registeredAddress") String registeredAddress ,
                                                                                   @Field("registeredTel") String registeredTel  ,
                                                                                   @Field("depositBank") String depositBank  ,
                                                                                   @Field("bankAccount") String bankAccount   ,
                                                                                   @Field("files") List<String> files);

    //用户修改增票资质
    @FormUrlEncoded
    @POST("invoice/updateVatInvoiceAptitudeById")
    Flowable<CommitVatInvoiceAptitudeToCheckModel> updateVatInvoiceAptitudeById(@Field("id") int id,
                                                                                   @Field("userId") int userId,
                                                                                   @Field("unitName") String unitName,
                                                                                   @Field("taxpayerIdentificationNumber") String taxpayerIdentificationNumber ,
                                                                                   @Field("registeredAddress") String registeredAddress ,
                                                                                   @Field("registeredTel") String registeredTel  ,
                                                                                   @Field("depositBank") String depositBank  ,
                                                                                   @Field("bankAccount") String bankAccount   ,
                                                                                   @Field("files") List<String> files);
    //用户删除增票资质
    @FormUrlEncoded
    @POST("invoice/deleteVatInvoiceAptitudeById")
    Flowable<CommitVatInvoiceAptitudeToCheckModel> deleteVatInvoiceAptitudeById(@Field("id") int id,
                                              @Field("userId") int userId);

    //修改用户头像
    @FormUrlEncoded
    @POST("user/updateUserPicUrl")
    Flowable<PostModel> updateUserPicUrl(@Field("userId") int userId,
                                         @Field("files") List<String> files);

    //修改用户名
    @FormUrlEncoded
    @POST("user/updateUserName")
    Flowable<PostModel> updateUserName(@Field("userId") int userId,
                                       @Field("name") String name);

    //根据用户id获取发票抬头单位信息列表
    @GET("invoice/getInvoiceUnitByUserId")
    Flowable<GeneralInvoiceModel> getInvoiceUnitByUserId(@Query("userId") int userId);

    //根据用户id和发票抬头单位信息id修改发票抬头单位（普票）信息
    @FormUrlEncoded
    @POST("invoice/updateInvoiceUnitById")
    Flowable<PostModel> updateInvoiceUnitById(@Field("id") int id,
                                              @Field("userId") int userId,
                                              @Field("unitName") String unitName,
                                              @Field("taxpayerIdentificationNumber") String taxpayerIdentificationNumber);

    //根据用户id和发票抬头单位资质id删除发票抬头单位（普票）信息
    @FormUrlEncoded
    @POST("invoice/deleteInvoiceUnitById")
    Flowable<PostModel> deleteInvoiceUnitById(@Field("id") int id,
                                              @Field("userId") int userId);

    //根据用户id获取消息信息列表
    @GET("activityInformation/getActivityMessageByUserId")
    Flowable<ActivityMessageByUserIdModel> getActivityMessageByUserId(@Query("userId") int userId);

    //三十、根据活动信息ID获取活动信息、活动相关商品列表信息、活动相关优惠券信息
    @GET("activityInformation/getActivityInformationById")
    Flowable<ActivityInformationByIdModel> getActivityInformationById(@Query("activityInformationId") int activityInformationId);

    //获取所有正在上线的预售活动
    @GET("activityInformation/getAllEffectPreSellActivityInformation")
    Flowable<EffectPreSellActivityInformationModel> getAllEffectPreSellActivityInformation();

    //用户领券
    @FormUrlEncoded
    @POST("couponInformation/userGetCoupon")
    Flowable<CommitVatInvoiceAptitudeToCheckModel> userGetCoupon(@Field("couponId") int couponId,
                                              @Field("userId") int userId);

    //购物返券
    @FormUrlEncoded
    @POST("couponInformation/shoppingBackCouponToUser")
    Flowable<CommitVatInvoiceAptitudeToCheckModel> shoppingBackCouponToUser(@Field("orderId") int orderId);

    //获取所有可以领取的优惠券
    @GET("couponInformation/getAllAvailableCoupon")
    Flowable<AllAvailableCouponModel> getAllAvailableCoupon();
    //根据用户id获取订单消息列表
    @GET("user/getOrderStateDetailList")
    Flowable<OrderMessageListModel> getOrderStateDetailList(@Query("userId") int userId);

    //根据用户id获取消息未读数
    @GET("user/getMessageNum")
    Flowable<MessageNoReadNumModel> getMessageNum(@Query("userId") int userId);

    //根据用户id删除所有消息
    @FormUrlEncoded
    @POST("user/deldectMessage")
    Flowable<PostModel> deldectMessage(@Field("userId") int userId);

    //根据userId修改订单消息为已读
    @FormUrlEncoded
    @POST("user/updateOrderMSGStauts")
    Flowable<PostModel> updateOrderMSGStauts(@Field("userId") int userId);

    //根据userId修改活动消息为已读
    @FormUrlEncoded
    @POST("user/updateActivityMSGStauts")
    Flowable<PostModel> updateActivityMSGStauts(@Field("userId") int userId);
    //微信获取授权
    @FormUrlEncoded
    @POST("sns/oauth2/access_token")
    Flowable<WXAccessTokenEntity> getAccessToken(@Field("appid") String  appid, @Field("secret") String  secret, @Field("code") String  code, @Field("grant_type") String  grant_type);

    //微信查询信息
    @FormUrlEncoded
    @POST("sns/userinfo")
    Flowable<WXUserInfo> getUserInfo(@Field("access_token") String  access_token, @Field("openid") String  openid);

    //获取某张优惠券对应的商品信息
    @GET("couponInformation/getMsgByCouponId")
    Flowable<CouponMsgModel> getMsgByCouponId(@Query("couponId") int couponId);

    //获取新版本信息
    @GET("versionMessage/getNewVersionMessage")
    Flowable<VersionMessageModel> getNewVersionMessage(@Query("isAndroidOriOS") int isAndroidOriOS);

    //获取支付宝authInfo（加签信息）
    @GET("AlipayApi/getAuthInfo")
    Flowable<AuthTask> getAuthInfo();

    //获取支付宝OrderInfo（订单信息）
    @POST("AlipayApi/getOrderInfo")
    Flowable<AuthTask> getOrderInfo(@Query("orderId") int orderId);

    @POST("AlipayApi/getOrderState")
    Flowable<AuthTask> getOrderState(@Query("orderId") int orderId);

    //获取微信OrderInfo（订单信息）
    @POST("WeChatApi/getOrderInfo")
    Flowable<WXOrderInfo> getWXOrderInfo(@Query("orderId") int orderId);

    //订单支付结果查询
    @POST("WeChatApi/orderQuery")
    Flowable<OrderPayResult> orderQuery(@Query("orderId") int orderId);

    //获取品牌列表
    @GET("goodsInformation/selectBrand")
    Flowable<GoodsBrand> getBrands();

    //根据商品id获取正在上线的且结束时间最早的预售活动信息
    @GET("activityInformation/getPreSellActivityInformationByGoodsDetailsId")
    Flowable<PresellMsg> getPreSellActivityInformationByGoodsDetailsId(@Query("goodsDetailsId") int goodsDetailsId,@Query("couponId") int couponId);

    //
    @FormUrlEncoded
    @POST("order/updateSatetIsOne")
    Flowable<PostModel> updateSatetIsOne(@Field("orderNo") String orderNo,@Field("totalFee") double totalFee,@Field("payMode") int payMode);
}
