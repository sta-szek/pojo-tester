require(['gitbook'], function(gitbook) {

  var githubOwner = "";
  var backgroundColor;
  var githubRepository = "";
  var spinner = "<div class='spinner'><div class='bounce1'></div><div class='bounce2'></div><div class='bounce3'></div></div>";


  var loadAndShowContributors = function(contributorsSection){
    var settings = {  "async": true,
                      "crossDomain": true,
                      "url": "https://api.github.com/repos/sta-szek/pojo-tester/stats/contributors",
                      "method": "GET"
                    }

    $.ajax(settings).done(function (response) {
      removeSpinner(contributorsSection);

      var users = $(contributorsSection);
      $.each(response, function(index,value){
        var userContent = createUserContent(value);
        $(users).append(userContent);
      });
      $(users).children().sort(function(a,b) {
        return a.children[0].dataset.weight > b.children[0].dataset.weight;
      }).appendTo(contributorsSection);
    });

  }

  var createUserContent = function(githubContribution){
    var additions = 0;
    var deletions = 0;
    $.each(githubContribution.weeks, function(){
      additions+=this.a;
      deletions+=this.d;
    });
    return $("<div class='contributor'></div>")
                           .css('background-color', backgroundColor)
                           .append("<div class='contributor-avatar' data-weight="+additions+"><img src='"+githubContribution.author.avatar_url+"'/></div>")
                           .append("<div class='contributor-data'><a href=''"+githubContribution.author.url+"'>"+githubContribution.author.login+"</a></br>additions "+additions+"</br>deletions "+deletions+"</div>");
  }

  var removeSpinner = function(contributorsSection){
    $(contributorsSection).html("");
  }

  var showSpinner = function(contributorsSection){
    $(contributorsSection).html(spinner);
  }

  gitbook.events.bind("page.change", function(event){
    var contributorsSection = $("#GitHubContributors");
    if(contributorsSection.length != 0){
      backgroundColor= $($('.book-summary')[0]).css('backgroundColor');
      showSpinner(contributorsSection);
      loadAndShowContributors(contributorsSection);
    }
  });

  gitbook.events.bind('start', function(e, config) {
    githubOwner = config.githubcontributors.githubOwner;
    githubRepository = config.githubcontributors.githubRepository;
   });

});
