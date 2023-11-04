<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email Verification Code</title>
    <style>
        /* Element UI样式 */
        .element-ui-blue {
            color: #409EFF;
        }

        /* 动画效果样式 */
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes rotate {
            to {
                transform: rotate(360deg);
            }
        }

        /* 其他样式，请根据需要自行添加 */

        /* 邮件内容容器 */
        .email-container {
            max-width: 600px;
            margin: 0 auto;
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.1);
            animation: fadeIn 0.8s ease-in-out;
        }

        /* 邮件标题 */
        .email-title {
            font-size: 32px;
            font-weight: bold;
            margin-bottom: 20px;
        }

        /* 动态效果的图标 */
        .rotating-icon {
            font-size: 48px;
            animation: rotate 2s linear infinite;
        }

        /* 按钮样式 */
        .copy-button {
            background-color: #409EFF;
            color: #fff;
            border: none;
            padding: 15px 30px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease-in-out;
            margin-top: 20px;
        }

        .copy-button:hover {
            background-color: #66b1ff;
        }
    </style>
</head>

<body>
<div class="email-container">
    <div class="email-title element-ui-blue">邮箱验证码</div>
    <p>亲爱的用户，您的邮箱验证码为：<strong class="element-ui-blue">${verificationCode}</strong></p>
    <div class="rotating-icon">&#128161;</div>
    <p>请勿将验证码告诉其他人。如非本人操作，请忽略此邮件。</p>
</div>

</body>

</html>
