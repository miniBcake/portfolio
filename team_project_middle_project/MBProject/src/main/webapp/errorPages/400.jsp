<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>400 Error - Bad Request</title>
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
        flex-direction: row; /* Row direction to place image and text side by side */
        align-items: center;
        justify-content: center;
        height: 100%;
        text-align: left;
        padding: 20px;
        box-sizing: border-box; /* Include padding in width/height calculations */
    }

    /* Style for the image */
    .image-container {
        flex: 1; /* Take 50% of the space */
        text-align: center;
    }
    .image-container img {
        max-width: 80%;
        height: auto;
    }

    /* Style for the text */
    .text-container {
        flex: 1; /* Take 50% of the space */
        padding: 20px;
    }

    h1 {
        font-size: 2.5em;
        color: #333;
        margin-bottom: 20px;
    }

    p {
        font-size: 1.2em;
        color: #666;
        margin-bottom: 30px;
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
            text-align: center; /* Center-align text on smaller screens */
        }
        .image-container {
            margin-bottom: 20px; /* Add space between image and text on smaller screens */
        }
        h1 {
            font-size: 2em;
        }
        p {
            font-size: 1em;
        }
    }

    @media (max-width: 480px) {
        h1 {
            font-size: 1.5em;
        }
        p {
            font-size: 0.9em;
        }
    }
</style>
</head>
<body>

<div class="container">
    <!-- Image on the left -->
    <div class="image-container">
        <img src="<%= request.getContextPath() %>/images/400-error-image.jpg" alt="400 Error Image" />
    </div>
    
    <!-- Text on the right -->
    <div class="text-container">
        <h1>400 Error - Bad Request</h1>
        <p>잘못된 요청입니다. 요청한 페이지를 찾을 수 없습니다.</p>
        <!-- HTTP Referer로 이전 페이지로 돌아가기 -->
        <a href="javascript:history.back()">이전 페이지로 돌아가기</a>
    </div>
</div>

</body>
</html>
