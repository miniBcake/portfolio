<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>500 Error - Internal Server Error</title>
<style>
    /* Ensure body and html take full height */
    html, body {
        height: 100%;
        margin: 0;
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
    }

    /* Flexbox container to place image and text side by side */
    .container {
        display: flex;
        flex-direction: row; /* Align image and text side by side */
        align-items: center;
        justify-content: center;
        height: 100%;
        text-align: left;
        padding: 20px;
        box-sizing: border-box; /* Include padding in width/height calculations */
    }

    /* Style for the image container */
    .image-container {
        flex: 1; /* Take 50% of the space */
        text-align: center;
    }

    /* Style for the image */
    .image-container img {
        max-width: 50%; /* Adjusted image size */
        height: auto;
    }

    /* Style for the text container */
    .text-container {
        flex: 1; /* Take 50% of the space */
        padding: 20px;
    }

    h1 {
        font-size: 2.5em; /* Adjusted for responsiveness */
        color: #333;
        margin-bottom: 20px;
    }

    p {
        font-size: 1.2em; /* Adjusted for responsiveness */
        color: #666;
        margin-bottom: 20px;
    }

    a {
        color: #0066cc;
        text-decoration: none;
        font-weight: bold;
    }

    /* Responsive adjustments */
    @media (max-width: 768px) {
        .container {
            flex-direction: column; /* Stack image and text vertically on smaller screens */
            text-align: center;
        }

        .image-container img {
            max-width: 70%; /* Slightly bigger image for smaller screens */
        }

        h1 {
            font-size: 2em; /* Smaller font size for tablets */
        }

        p {
            font-size: 1em; /* Smaller font size for tablets */
        }
    }

    @media (max-width: 480px) {
        h1 {
            font-size: 1.5em; /* Smaller font size for mobile devices */
        }

        p {
            font-size: 0.9em; /* Smaller font size for mobile devices */
        }

        .image-container img {
            max-width: 80%; /* Bigger image for small mobile screens */
        }
    }
</style>
</head>
<body>

<div class="container">
    <!-- Image on the left -->
    <div class="image-container">
        <img src="<%= request.getContextPath() %>/images/500-error-image.jpg" alt="500 Error Image" />
    </div>

    <!-- Text on the right -->
    <div class="text-container">
        <h1>500 Error - Internal Server Error</h1>
        <p>서버 내부에서 오류가 발생했습니다. 불편을 드려 죄송합니다.</p>
        <p>문제가 계속될 경우, 관리자에게 문의하세요.</p>
        <!-- HTTP Referer로 이전 페이지로 돌아가기 -->
        <a href="javascript:history.back()">이전 페이지로 돌아가기</a>
    </div>
</div>

</body>
</html>
