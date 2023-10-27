package com.duofan.fly.api.pay.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 当面付配置配置类
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/27
 */


@Data
@ConfigurationProperties(prefix = "fly.pay.alipay")
public class AlipayTradeProperty {


    @Data
    public static class FaceTrade {
        public String AppId = "2019100167926897";
        public String PrivateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDGjPXPfj7DsQgG07cHtySQvv1+Hw5KLpgWFAB0xQdVxIZoihWrJ/vr9KIBP4gxsc6yyYu3VbIZ4lFSdipJtFjUBmrTbpp9p1MOHhqxQ8BNh3NpF/ZCAtt8DoLgrlwqd/vs1DJEv2nHef+c3px4YeM3W6BU2vS0H9Mr30HCIOGyX4cMto+n7MWNf1BLOatTOEzb0kRZ0NBs7rYtoD3PnmJV7OlzY/JgKtTTy7sctu/Pf4l5dp/nDUGWV0mf0U8R9HgllKQVF2AE5+uSZxesSI2zwvoSAU6M0Wa5ZNCaMKf+P82s/EAQgAgkHiDp9OJjYnM/fHeA8EqNb/Z1bX6qr0fbAgMBAAECggEBALSAEzuFvpNyziQ+tkb6VPCN4K+WlgwUXci8eEmvkOKE3dgNqHNHzzsL03elBTCAP7PDjBOogVgMqfd4WytUG+jsyJLcGvCee9/6uDNsr1cMC3x//yy1okkefBa8DCfDmjAMHILpLMXef+Vhbwf1nXWHA7QAtbNGt1hMB2DvOZ9Iodk++hc3wjnJIlNqkI8B0mBhf0jBKEYUrqKe1siCglHNmFXfG+0/0WyzhOeCWwV1ik8TrOD2EIZ5zOEuTi7ufhKFB3ByOq6YcgSTNh5Zy4EF0sYhCwcpJpo4UDEf1sLoSfKPUkdudFnebF7/9DravEPhVm/3rcjWZmKKZsR+RYkCgYEA/wUytAJSrF/rNIiNPs4E8G0CXMuosEuLFzOHUVzHKn/nB6F8p93ut42YgoszWe/7Ddhxw3tszZWWqt/FPXIDymqo5+mQJjz8B/yhG/Zxj7+ONZfCNEkL1xPYmIBUT0G2/YE8HfPv5gRIMFJvjtihHvLGSHmlJLbf4majyHRehW8CgYEAx1A5/olD3RfkFsGvjDyo/2W3YBwn8MzLKrmsVBYXuP85uy9tEsKi9/io9JafLn4VtSxwFpefQsWhkd5QKK9MYZp5ozdkpg+7y+EiXbj6E32H7LV1GICNwDQRtfqJbEDx5HYLrR+L0OD3NYLQJDnups8LXMeY2AmrGISjO4IpplUCgYEA7NMp8+sYVYW6Co4pBUMTaScCg6SJ4jY1U/WBW7iaKMighwCbn9TFBGaVU93rcUEgVwpDE7OCJiUdx0+Jfvdrt8V3V+8Z87i8oLol767cZQ8SBIkLS7zuJu0CPxUltnijdCZY6sQHbAdb9qwHd5OLhQ8VvmZhS4gcv8ZroRMYWDcCgYAWllXsLZweOy3jElIVn247e0h7wNUZ6euATQhlgxdvJKVjPC2tOZeEaI6lIUiq2SkWnhGZzdcDlygHQt1srukGCp6MS6PBWfkbUsk1O9hDSy+TOMErZK4rwPrc16apOPbwwBv0o6WRp1z8mHWEFXihOMd93TTKDtZv8eAZx1d2/QKBgQDCiSqhpGogVej6yHzvWqRqj5FooOiORd3FGENUPoREmJE7py5X8l9R1PCFqL1TY6yuzYJyZ0y2Oq3UxbzoZ2lEqGgaexuZRYoz6HjB/8jfcTmCCr2mWM7dp91D2m8o8WQ7FCtBFjHGAczfDcd4vd8yGZ/7MS2dEc+jcBZuOxyUig==";
        // 支付宝公钥
        public String PublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh+dISKRpI8jj+qzZFN0Gw1J7ZAa3FINMypZRS3yxxZmkoI6Ihnosih3yANmsgzwt+6ZHxulhiFFPUk9zIcxHtAcYJEqcRt4zMDGql2KiCOwSRva4AgwU8WEt2cEIL3xOhOlrGrBvooNFZSYHMTBlbPLoa5L8S7xbyczxFFXN9d9kpbyTGkqoe9h5/MH5AO8hsLqR21H7JnMh6FT7joix3WClysVNLgAZgGyouMyYrzCgafB6N6bnnzgxYh53ERlY6j0sYtqIO8BczwhVe3yu0pGlukkqVNch8YmmhokYB0Zj00z8kF3ZYC08xIb+RfIwN1GA7A9K6drAQ/ZcPjVE9wIDAQAB";
        public String EncryptKey = "jbgWjMJiy874+ARX6JGKew==";

        public String ServerUrl = "https://openapi.alipaydev.com/gateway.do";


        public String SignType = "RSA2";

        public String Charset = "UTF8";
        public String Format = "json";
        public String NotifyUrl = "https://alipay.duofan.top/face-trade/callback";

    }
}
