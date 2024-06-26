package com.suhyeong.yire.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * ITunesSearch App 을 사용하면서 발생하는 에러 정보.
 */
@Suppress("Unused")
sealed class ItsAppError(msg: String) : RuntimeException(msg) {
    /**
     * 유효하지 않은 토큰 에러인지 체크
     */
    fun isInvalidTokenError(): Boolean {
        when (this) {
            is ClientError -> {
                if (reason == ClientErrorCause.TokenNotFound) {
                    return true
                }
            }
            is ApiError -> {
                if (reason == ApiErrorCause.InvalidToken) {
                    return true
                }
            }
        }
        return false
    }
}

/**
 * API 에러
 */
@Parcelize
data class ApiError(
    val statusCode: Int,
    val reason: ApiErrorCause,
    override val message: String
) : ItsAppError(message), Parcelable

/**
 * Client 에러
 */
@Parcelize
data class ClientError(
    val reason: ClientErrorCause
) : ItsAppError(reason.description), Parcelable


/**
 * [ClientError]의 발생 원인
 */
enum class ClientErrorCause(val description: String) {
    /** 기타 에러 */
    Unknown("unknown error."),

    /** API 요청에 사용할 토큰이 없음 */
    TokenNotFound("authentication tokens don't exist."),

    /** 정상적으로 실행할 수 없는 상태 */
    IllegalState("illegal state.")
}

/**
 * [ApiError]의 발생 원인
 */
enum class ApiErrorCause(val errorCode: Int) {

    /** 서버 내부에서 처리 중에 에러가 발생한 경우 */
    @SerializedName("-1")
    InternalError(-1),

    /** 필수 인자가 포함되지 않은 경우나 호출 인자값의 데이타 타입이 적절하지 않거나 허용된 범위를 벗어난 경우 */
    @SerializedName("-2")
    IllegalParams(-2),

    /** 해당 API를 사용하기 위해 필요한 기능(간편가입, 동의항목, 서비스 설정 등)이 활성화 되지 않은 경우 */
    @SerializedName("-3")
    UnsupportedApi(-3),

    /** 계정이 제재된 경우나 해당 계정에 제재된 행동을 하는 경우 */
    @SerializedName("-4")
    BlockedAction(-4),

    /** 해당 API에 대한 요청 권한이 없는 경우 */
    @SerializedName("-5")
    PermissionDenied(-5),

    /** 서비스가 종료된 API를 호출한 경우 */
    @SerializedName("-9")
    DeprecatedApi(-9),

    /** 허용된 요청 회수가 초과한 경우 */
    @SerializedName("-10")
    ApiLimitExceeded(-10),

    /** 해당 앱에 카카오계정 연결이 완료되지 않은 사용자가 호출한 경우 */
    @SerializedName("-101")
    NotRegisteredUser(-101),

    /** 이미 연결된 사용자에 대해 signup 시도 */
    @SerializedName("-102")
    AlreadyRegisteredUser(-102),

    /** 존재하지 않는 카카오계정으로 요청한 경우 */
    @SerializedName("-103")
    AccountDoesNotExist(-103),

    /** 사용자 정보 요청 API나 사용자 정보 저장 API 호출 시 앱에 추가하지 않은 사용자 프로퍼티 키 값을 불러오거나 저장하려고 한 경우 */
    @SerializedName("-201")
    PropertyKeyDoesNotExist(-201),

    /** 등록되지 않은 앱키의 요청 또는 존재하지 않는 앱으로의 요청. (앱키가 인증에 사용되는 경우는 -401 참조) */
    @SerializedName("-301")
    AppDoesNotExist(-301),

    /** 유효하지 않은 앱키나 액세스 토큰으로 요청한 경우, 등록된 앱 정보와 호출된 앱 정보가 불일치 하는 경우 ex) 토큰 만료 */
    @SerializedName("-401")
    InvalidToken(-401),

    /** 해당 API에서 접근하는 리소스에 대해 사용자의 동의를 받지 않은 경우 */
    @SerializedName("-402")
    InsufficientScope(-402),

    /** 앱의 연령제한에 대해 사용자 연령 인증 받지 않음 */
    @SerializedName("-405")
    RequiredAgeVerification(-405),

    /** 앱의 연령제한보다 사용자의 연령이 낮음 */
    @SerializedName("-406")
    UnderAgeLimit(-406),

    /** 카카오톡 미가입 사용자가 카카오톡 API를 호출하였을 경우 */
    @SerializedName("-501")
    NotTalkUser(-501),

    /** 받는 이가 보내는 이의 친구가 아닌 경우 */
    @SerializedName("-502")
    NotFriend(-502),

    /** 지원되지 않는 기기로 메시지 보내는 경우 */
    @SerializedName("-504")
    UserDeviceUnsupported(-504),

    /** 받는 이가 메시지 수신 거부를 설정한 경우 */
    @SerializedName("-530")
    TalkMessageDisabled(-530),

    /** 특정 앱에서 보내는 이가 특정인에게 하루 동안 보낼 수 있는 쿼터를 초과한 경우 */
    @SerializedName("-531")
    TalkSendMessageMonthlyLimitExceed(-531),

    /** 특정 앱에서 보내는 이가 받는 사람 관계없이 하루 동안 보낼 수 있는 쿼터를 초과한 경우 */
    @SerializedName("-532")
    TalkSendMessageDailyLimitExceed(-532),

    /** 카카오스토리 가입 사용자에게만 허용된 API에서 카카오스토리 미가입 사용자가 요청한 경우 */
    @SerializedName("-601")
    NotStoryUser(-601),

    /** 카카오스토리 이미지 업로드 사이즈 제한 초과 */
    @SerializedName("-602")
    StoryImageUploadSizeExceeded(-602),

    /** 업로드,스크랩 등 오래 걸리는 API의 타임아웃 */
    @SerializedName("-603")
    TimeOut(-603),

    /** 카카오스토리에서 스크랩이 실패하였을 경우*/
    @SerializedName("-604")
    StoryInvalidScrapUrl(-604),

    /** 카카오스토리에 존재하지 않는 내스토리를 요청했을 경우 */
    @SerializedName("-605")
    StoryInvalidPostId(-605),

    /** 카카오스토리에서 업로드할 수 있는 최대 이미지 개수(현재 5개. 단, gif 파일은 1개)를 초과하였을 경우 */
    @SerializedName("-606")
    StoryMaxUploadCountExceed(-606),

    /** 등록되지 않은 개발자의 앱키나 등록되지 않은 개발자의 앱키로 구성된 액세스 토큰으로 요청한 경우 */
    @SerializedName("-903")
    DeveloperDoesNotExist(-903),

    /** 서버 점검 중 */
    @SerializedName("-9798")
    UnderMaintenance(-9798),

    /** 기타 에러 */
    @SerializedName("-${Int.MAX_VALUE}")
    Unknown(Int.MAX_VALUE);
}