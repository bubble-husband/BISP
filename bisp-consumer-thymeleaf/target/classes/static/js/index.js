
    //网站最后更新时间（版本更新需更改）
    var siteLastUpdateTime = '2020年4月22日19点';

    //网站开始时间
    var siteBeginRunningTime = '2020-06-01 20:00:00';

    

    //填充文章
    function putInArticle(data) {
    $('.articles').empty();
    var articles = $('.articles');
    $.each(data, function (index, obj) {
        if(index != (data.length) - 1){
            var center = $('<div class="center">' +
                '<header class="article-header">' +
                '<h1 itemprop="name">' +
                '<a class="article-title" href="' + obj['thisArticleUrl'] + '" target="_blank">' + obj['articleTitle'] + '</a>' +
                '</h1>' +
                '<div class="article-meta row">' +
                '<span class="articleType am-badge am-badge-success">' + obj['articleType'] + '</span>' +
                '<div class="articlePublishDate">' +
                '<i class="am-icon-calendar"><a class="linkColor" href="/archives?archive=' + obj['publishDate'] + '"> ' + obj['publishDate'] + '</a></i>' +
                '</div>' +
                '<div class="originalAuthor">' +
                '<i class="am-icon-user"> ' + obj['originalAuthor'] + '</i>' +
                '</div>' +
                '<div class="categories">' +
                '<i class="am-icon-folder"><a class="linkColor" href="/categories?category=' + obj['articleCategories'] + '"> ' + obj['articleCategories'] + '</a></i>' +
                '</div>' +
                '</div>' +
                '</header>' +
                '<div class="article-entry">' +
                obj['articleTabloid'] +
                '</div>' +
                '<div class="read-all">' +
                '<a href="' + obj['thisArticleUrl'] + '" target="_blank">阅读全文 <i class="am-icon-angle-double-right"></i></a>' +
                '</div>' +
                '<hr>' +
                '<div class="article-tags">' +

               '</div>' +
                '</div>');
            articles.append(center);
            var articleTags = $('.article-tags');
            for(var i=0;i<obj['articleTags'].length;i++){
                var articleTag = $('<i class="am-icon-tag"><a class="tag" href="/tags?tag=' + obj['articleTags'][i] + '"> ' + obj['articleTags'][i] + '</a></i>');
                articleTags.eq(index).append(articleTag);
            }
            // var likes = $('<span class="likes"><i class="am-icon-heart"> ' + obj['likes'] + '个喜欢</i></span>');
            // articleTags.eq(index).append(likes);
        }
    })

}

    //填充最新评论
    function putInNewComment(data) {
        var newComment = $('.new-comment');
        newComment.empty();
        var listNews = $('<div data-am-widget="list_news" class="am-list-news am-list-news-default" ></div>');
        var newCommentTitle = $('<div class="am-list-news-hd am-cf">' +
            '<a class="newComments">' +
            '<h2 style="color: #110101">最新评论</h2>' +
            '</a>' +
            '</div>');
        listNews.append(newCommentTitle);
        var amListNewsBd = $('<div class="am-list-news-bd"></div>');
        var ul = $('<ul class="fiveNewComments am-list"></ul>');
        $.each(data['result'],function (index,obj) {
            var li = $('<li class="am-g am-list-item-dated">' +
                '<a class="newCommentTitle" target="_blank" href="/article/' + obj['articleId']  + '#p' + obj['id'] + '" class="am-list-item-hd" style="padding-bottom: 5px" title="' + obj['articleTitle'] + '">'+ obj['articleTitle'] +'</a>' +
                '<span class="am-list-date">' + obj['commentDate'] + '</span>' +
                '<div class="new-comment-content" style="margin-bottom: 5px;">' + obj['answerer'] + '：' + obj['commentContent'] + '</div>' +
                '</li>');
            ul.append(li);
        });
        amListNewsBd.append(ul);
        listNews.append(amListNewsBd);
        newComment.append(listNews);
        newComment.append($('<div class="my-row" id="page-father">' +
            '<div class="newCommentPagination">' +
            '</div>' +
            '</div>'));
    }





    function newCommentAjax(currentPage) {
    //最新评论
    $.ajax({
        type: 'GET',
        url: '/newComment',
        dataType: 'json',
        data: {
            rows:"5",
            pageNum:currentPage
        },
        success: function (data) {
            if(data['status'] == 103){
                dangerNotice(data['message'] + " 获得最新评论失败")
            } else {
                putInNewComment(data['data']);

                //分页
                $(".newCommentPagination").paging({
                    rows:data['data']['pageInfo']['pageSize'],//每页显示条数
                    pageNum:data['data']['pageInfo']['pageNum'],//当前所在页码
                    pages:data['data']['pageInfo']['pages'],//总页数
                    total:data['data']['pageInfo']['total'],//总记录数
                    flag:0,
                    callback:function(currentPage){
                        newCommentAjax(currentPage);
                    }
                });
            }
        },
        error: function () {
        }
    });
}



    //网站信息
    $.ajax({
        type: 'GET',
        url: '/getSiteInfo',
        dataType: 'json',
        data: {
        },
        success: function (data) {
            if(data['status'] == 103){
                dangerNotice(data['message'] + " 获得网站信息失败")
            } else {
                var siteInfo = $('.site-info');
                siteInfo.empty();
                siteInfo.append('<h5 class="site-title">' +
                    '<i class="am-icon-info site-icon"></i>' +
                    '<strong style="margin-left: 15px">网站信息</strong>' +
                    '</h5>');
                var siteDefault = $('<ul class="site-default"></ul>');
                siteDefault.append('<li>' +
                    '<i class="am-icon-file site-default-icon"></i><span class="site-default-word">文章总数</span>：' + data['data']['articleNum'] + ' 篇' +
                    '</li>');
                siteDefault.append('<li>' +
                    '<i class="am-icon-tags site-default-icon"></i><span class="site-default-word">标签总数</span>：' + data['data']['tagsNum'] + ' 个' +
                    '</li>');
                siteDefault.append('<li>' +
                    '<i class="am-icon-comments-o site-default-icon"></i><span class="site-default-word">留言总数</span>：' + data['data']['leaveWordNum'] + ' 条' +
                    '</li>');
                siteDefault.append('<li>' +
                    '<i class="am-icon-commenting-o site-default-icon"></i><span class="site-default-word">评论总数</span>：' + data['data']['commentNum'] + ' 条' +
                    '</li>');
                siteDefault.append('<li>' +
                    '<i class="am-icon-pencil-square site-default-icon"></i><span class="site-default-word">网站最后更新</span>：<span class="siteUpdateTime">' + siteLastUpdateTime + '</span>' +
                    '</li>');
                siteDefault.append('<li>' +
                    '<i class="am-icon-calendar site-default-icon"></i><span class="site-default-word">网站运行天数</span>：<span class="siteRunningTime"> </span>' +
                    '</li>');
                siteInfo.append(siteDefault);
            }
        },
        error: function () {
        }
    });

    //网站运行时间
    //beginTime为建站时间的时间戳
    function siteRunningTime(time) {
        var theTime;
        var strTime = "";
        if (time >= 86400){
            theTime = parseInt(time/86400);
            strTime += theTime + "天";
            time -= theTime*86400;
        }
        if (time >= 3600){
            theTime = parseInt(time/3600);
            strTime += theTime + "时";
            time -= theTime*3600;
        }
        if (time >= 60){
            theTime = parseInt(time/60);
            strTime += theTime + "分";
            time -= theTime*60;
        }
        strTime += time + "秒";

        $('.siteRunningTime').html(strTime);
    }

    var nowDate = new Date().getTime();
    //网站开始运行日期
    var oldDate = new Date(siteBeginRunningTime.replace(/-/g,'/'));
    var time = oldDate.getTime();
    var theTime = parseInt((nowDate-time)/1000);
    setInterval(function () {
        siteRunningTime(theTime);
        theTime++;
    },1000);