var backend = "/api";
		
$(function(){
    $(".action-form").submit(function(e){
        return $(this).ActionHandler(e, $(this).serialize());
    });

    $(".action-link").click(function(e){
        return $(this).ActionHandler(e, $(this).data("data"));
    });

    $(".select-fill").each(function(){
        if($(this).data("href")){
            $.ajax({
                url: backend + $(this).data("href"),
                type: ($(this).data("method") ? $(this).data("method") : "GET"),
                data: $(this).data("data"),
                //dataType: "json",
                success: $.proxy(function(response){
                    for(var i = 0; i < response.length; i++){
                        name = response[i];
                        $(this).append($("<option/>").html(name));

                        //hack: add to customers table
                        $button = $("<button/>").addClass("btn btn-danger btn-xs action-link").data({
                            redirect:"self",
                            method: "delete"
                        }).attr("href", "/customers/"+name).text("Delete").click(function(e){
                            return $(this).ActionHandler(e);
                        });

                        $buttonAnalyze = $("<button/>").addClass("btn btn-info btn-xs action-link").data({
                            method: "get",
                            callback: "Analyze"
                        }).attr("href", "/twitter/analyze/"+name).text("Analyze").click(function(e){
                            $("#analyze").hide();
                            $("#loading-indicator").fadeIn();
                            return $(this).ActionHandler(e);
                        });
                        $tdName = $("<td/>").text(name);
                        $tdButton = $("<td/>").append($buttonAnalyze).append($button);
                        $tr = $("<tr/>").append($tdName).append($tdButton);
                        $("#table_customers").append($tr);
                    }
                }, this)
            });
        }
    });

    $(".select-customer").change(function(){
        $.ajax({
            url: backend + "/keywords/"+$(this).val(),
            type: "GET",
            success: $.proxy(function(response){
                $("#keywords").val(response.toString());
                $(".form-keywords").attr("action", "/keywords/"+$(this).val());
            }, this)
        });
    });

    $(".data-poller").each(function(){
        var timeout = ($(this).data("timeout") ? $(this).data("timeout") : 5);
        setInterval($.proxy(function(){
            if($(this).data("active")){
                $.ajax({
                    url: backend + $(this).data("href"),
                    type: "GET",
                    success: $.proxy(function(response) {
                        CallbackHandler($(this).data("callback"), response)
                    }, this)
                });
            }
        }, this), timeout * 1000);
    });

    // Load config
    $.ajax({
        url: backend + "/config",
        type: "GET",
        success: function(response){
            $json = $.parseJSON(response);
            $("#jsonFile").val($json["jsonFile"]);
            $("#algorithm").val($json["algorithm"]);
            $("#ignoreAccounts").val($json["ignoreAccounts"]);
            $("#neutral").prop("checked", $json["classifier_neutral"]);
            $("#normalizeDocLength").prop("checked", $json["classifier_normalizeDocLength"]);
            $("#newWordsToKeep").val($json["classifier_newWordsToKeep"]);
            $("#outputWordCounts").prop("checked", $json["classifier_outputWordCounts"]);
            $("#tfidfTransofrm").prop("checked", $json["classifier_tfidfTransofrm"]);
            $("#classifier_useDistribution").prop("checked", $json["classifier_useDistribution"]);
            $("#language").val($json["language"]);
            $("#trainingFile").val($json["trainingFile"]);

            $("#bayes_useKernelEstimator").prop("checked", $json["bayes_useKernelEstimator"]);
            $("#bayes_useSupervisedDiscretization").prop("checked", $json["bayes_useSupervisedDiscretization"]);

            $("#j48_binarySplits").prop("checked", $json["j48_binarySplits"]);
            $("#j48_confidenceFactor").val($json["j48_confidenceFactor"]);
            $("#j48_minNumObj").val($json["j48_minNumObj"]);
            $("#j48_numFolds").val($json["j48_numFolds"]);
            $("#j48_errorPruning").prop("checked", $json["j48_errorPruning"]);
            $("#j48_saveInstanceData").prop("checked", $json["j48_saveInstanceData"]);
            $("#j48_newSeed").val($json["j48_newSeed"]);
            $("#j48_unpruned").prop("checked", $json["j48_unpruned"]);
            $("#j48_useLaplace").prop("checked", $json["j48_useLaplace"]);

            $("#rf_maxDepth").val($json["rf_maxDepth"]);
            $("#rf_newNumFeatures").val($json["rf_newNumFeatures"]);
            $("#rf_newNumTrees").val($json["rf_newNumTrees"]);
            $("#rf_seed").val($json["rf_seed"]);

            $("#kN_newCrossValidate").prop("checked", $json["kN_newCrossValidate"]);
            $("#kN_k").val($json["kN_k"]);
            $("#kN_newMeanSquared").prop("checked", $json["kN_newMeanSquared"]);
            $("#kN_newWindowSize").val($json["kN_newWindowSize"]);
        }
    });
});

$.fn.ActionHandler = function(e){

    var method = "GET";
    var href = "undefined";
    var data = null;

    if($(this).is("form")){
        if($(this).attr("method")) method = $(this).attr("method");
        if($(this).attr("action")) href = $(this).attr("action");
        data = $(this).serialize();
    }else{
        if($(this).data("method")) method = $(this).data("method");
        if($(this).data("href")) href = $(this).data("href");
        if($(this).attr("href")) href = $(this).attr("href");
        if($(this).data("data")) data = $(this).data("data");
    }

    $.ajax({
        url: backend + href,
        type: method,
        data: data,
        //dataType: "json",
        success: $.proxy(function(response){
            $(this).AjaxSuccessHandler(response);
        }, this)
    });

    e.preventDefault();
    return false;
}

$.fn.AjaxSuccessHandler = function(response){
	if(typeof $(this).data("redirect") !== "undefined"){
		var redirectTo = $(this).data("redirect");
		
		if(redirectTo.toLowerCase() == "self"){
			window.location.reload();
		}else{
			window.location.href = redirectTo;
		}
	}
	
	if(typeof $(this).data("callback") !== "undefined"){
		CallbackHandler($(this).data("callback"), response);//$(this).data("data"));
	}
};

function CallbackHandler(callback, data){
	switch(callback){
        case "ReloadKeywords":
            ReloadKeywords();
            break;
        case "StartCollect":
            StartCollect();
            break;
        case "StopCollect":
            StopCollect();
            break;
        case "TweetsPolling":
            TweetsPolling($.parseJSON(data));
            break;
        case "StatusPolling":
            StatusPolling(data);
            break;
        case "Analyze":
            Analyze($.parseJSON(data));
            break;
        default: break;
	}
}

function Analyze(data){
    $(".analyze_name").text(data["customer"]);
    $(".analyze_algorithm").text(data["algorithm"]);
    $(".analyze_tweets_size").text(data["size"]);
    $(".analyze_result").text(data["result"]);
    $(".analyze_positives").text(data["positives"]);
    $(".analyze_neutrals").text(data["neutrals"]);
    $(".analyze_negatives").text(data["negatives"]);

    var resultAsProcent = parseInt(parseFloat(data["result"]) * 100);
    var resultForProgressBar = resultAsProcent + "%";
    var clazz = "progress-bar-success";
    if(resultAsProcent < 40){
        clazz = "progress-bar-danger";
    }else if(resultAsProcent >= 40 && resultAsProcent < 70){
        clazz = "progress-bar-warning";
    }

    $(".progress-bar").width(resultForProgressBar).attr("atria-valuenow", resultForProgressBar).text(resultForProgressBar).attr("class", "progress-bar "+clazz);

    $(".tweets").html(ToList(data["tweets"], "text"));
    $(".preprocessed").html(ToList(data["tweets"], "preprocessedText", "classified"));

    $("#loading-indicator").hide();
    $("#analyze").show();
}

function ToList(list, name, name2){
    $ul = $("<ul/>");

    for(var i = 0; i < list.length; i++){
        var text = list[i][name];
        if(typeof(name2) != "undefined"){
            text = list[i][name2] + ": " + text;
        }

        $ul.append($("<li/>").text(text));
    }

    return $ul;
}

function TweetsPolling(data){
    for(var i = 0; i < data.length; i++){
        $("#stream").find("ul").append($("<li/>").text(data[i] + "<br/>"));
    }
}

function StatusPolling(data){
    if(data == "stopped"){
        // hide stop button
        $(".stop-collect").hide();
        $(".start-collect").show();
    }else{
        $(".stop-collect").show();
        $(".start-collect").hide();
    }
    $("#server_status").text(data);
}

function ReloadKeywords(){
    $(".select-customer").change();
}

function StartCollect(){
    $("#stream").data("active", true);
    StatusPolling("collecting");
}

function StopCollect(){
    $("#stream").data("active", false);
    StatusPolling("stopped");
}
