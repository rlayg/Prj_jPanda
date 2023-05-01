package com.kakao.jPanda.yjh.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.kakao.jPanda.yjh.domain.CompanySalesDto;
import com.kakao.jPanda.yjh.domain.CouponDto;
import com.kakao.jPanda.yjh.domain.ExchangeDto;
import com.kakao.jPanda.yjh.domain.NoticeDto;
import com.kakao.jPanda.yjh.domain.TalentDto;
import com.kakao.jPanda.yjh.domain.TalentRefundDto;

public interface AdminService {
	//notice
	List<NoticeDto> findNotice();
	NoticeDto findNoticeByNoticeNo(String noticeNo);
	String modifyNotice(NoticeDto notice);
	String addNotice(NoticeDto notice);
	
	//exchange
	List<ExchangeDto> findExchange();
	int modifyExchangeStatusByExchangeNos(List<ExchangeDto> exchangeDto);
	
	//coupon
	List<CouponDto> findCouponsExpired();
	String generateCouponNo();
	Map<String, Integer> addCoupon(CouponDto couponDto);
	
	//company-sales
	List<CompanySalesDto> findCompanySalesByStartDateAndEndDate(Timestamp startDate, Timestamp endDate);
	
	//talent
	List<TalentDto> findTalent();
	int modifyTalentByTalentNos(List<TalentDto> talentDto);
	
	//talent-refund
	List<TalentRefundDto> findTalentRefund();
	int modifyTalentRefundByPurchaseNosAndStatus(List<TalentRefundDto> talentRefundDto);

}
