function getCookie(remoteUser) {
    let cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        let cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            let cookie = cookies[i].trim();
            // 判断这个cookie的参数名是不是我们想要的
            if (cookie.substring(0, remoteUser.length + 1) === (remoteUser + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(remoteUser.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}