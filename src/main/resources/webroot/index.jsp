<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />

    <title>Sentiment Analysis</title>

    <link href="resource/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link href="resource/css/sentiment.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="resource/js/jquery-2.1.3.min.js"></script>
</head>
<body>
<div class="container content" role="main">

    <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
        <li class="active"><a href="#customers" data-toggle="tab">Customers</a></li>
        <li><a href="#keys" data-toggle="tab">Keywords</a></li>
        <li><a href="#live" data-toggle="tab">Stream</a></li>
        <li><a href="#config" data-toggle="tab">Configuration</a></li>
        <li class="pull-right server-tab">
            <span>Server: </span>
            <span id="server_status" class="data-poller" data-href="/twitter" data-timeout="5" data-active="true" data-callback="StatusPolling">getting server info...</span>
            <button class="btn btn-default action-link btn-success btn-sm start-collect" href="/twitter" data-method="POST" data-callback="StartCollect">Start</button>
            <button class="btn btn-default action-link btn-danger btn-sm stop-collect" href="/twitter" data-method="DELETE" data-callback="StopCollect">Stop</button>
        </li>
    </ul>

    <div class="tab-content">
        <div class="tab-pane active" id="customers">
            <h1>Customers</h1>

            <form class="form-inline action-form" method="post" action="/customers" data-redirect="self">
                <div class="form-group">
                    <div class="input-group">
                        <input type="text" class="form-control" id="name" name="name" placeholder="Customer" value="" required/>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Add</button>
            </form>

            <table class="table" id="table_customers">
                <thead>
                    <tr>
                        <th>Customer</th>
                        <th>Action</th>
                    </tr>
                </thead>
            </table>

            <img src="resource/img/ajax-loader.gif" id="loading-indicator" style="display:none" />

            <div id="analyze">
                <h2>Analyze Result</h2>
                <table class="table">
                    <thead>
                        <tr>
                            <th class="col-md-2"></th>
                            <th>Value</th>
                        </tr>
                    </thead>
                    <tr>
                        <td><label>Customer</label></td>
                        <td class="analyze_name"></td>
                    </tr>
                    <tr>
                        <td><label>Classifier</label></td>
                        <td class="analyze_algorithm"></td>
                    </tr>
                    <tr>
                        <td><label>Tweets</label></td>
                        <td class="analyze_tweets_size"></td>
                    </tr>
                    <tr>
                        <td><label>Positives</label></td>
                        <td class="analyze_positives"></td>
                    </tr>
                    <tr>
                        <td><label>Neutrals</label></td>
                        <td class="analyze_neutrals"></td>
                    </tr>
                    <tr>
                        <td><label>Negatives</label></td>
                        <td class="analyze_negatives"></td>
                    </tr>
                    <tr>
                        <td><label>Result</label></td>
                        <td>
                            <span class="analyze_result"></span>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
                                    60%
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>

                <h3>Details</h3>
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title">
                                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                                    Tweets
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse tweet-container" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body tweets">
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingTwo">
                            <h4 class="panel-title">
                                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                    Preprocessing
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse tweet-container" role="tabpanel" aria-labelledby="headingTwo">
                            <div class="panel-body preprocessed">
                            </div>
                        </div>
                    </div>
                </div>
            </div>


        </div>

        <div class="tab-pane" id="keys">
            <h1>Keywords</h1>
            <div class="form-group">
                <select class="form-control select-fill select-customer" id="Customer" data-href="/customers">
                    <option>Please select</option>
                </select>
            </div>
            <form class="form action-form form-keywords" method="put" action="/keywords/{val}" data-callback="ReloadKeywords">
                <div class="form-group">
                    <textarea class="form-control" id="keywords" name="keywords" rows="3"></textarea>
                </div>

                <button type="submit" class="btn btn-primary">Set</button>
            </form>
        </div>

        <div class="tab-pane" id="live">
            <h1>Stream</h1>
            <div id="stream" class="data-poller" data-href="/twitter/stream" data-timeout="5" data-active="false" data-callback="TweetsPolling">
                <ul></ul>
            </div>
        </div>

        <div class="tab-pane" id="config">
            <h1>Configuration</h1>
            <form class="form action-form" method="PUT" action="/config">
                <div class="form-group">
                    <label class="col-sm-2">JSON-File</label>
                    <input class="form-control" type="text" id="jsonFile" name="jsonFile" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">TrainingData</label>
                    <input class="form-control" type="text" id="trainingFile" name="trainingFile" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">Language</label>
                    <input class="form-control" type="text" id="language" name="language" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">Algorithm</label>
                    <select class="form-control" id="algorithm" name="algorithm">
                        <option>NaiveBayes</option>
                        <option>J48</option>
                        <option>NaiveBayesMultinomial</option>
                        <option>RandomForest</option>
                        <option>kNearestNeighbour</option>
                    </select>
                </div>
                <div class="form-group">
                    <label class="col-sm-2">Neutral</label>
                    <input class="form-control" type="checkbox" id="neutral" name="neutral" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">normalizeDocLength</label>
                    <input class="form-control" type="checkbox" id="normalizeDocLength" name="normalizeDocLength" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">newWordsToKeep</label>
                    <input class="form-control" type="number" id="newWordsToKeep" name="newWordsToKeep" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">outputWordCounts</label>
                    <input class="form-control" type="checkbox" id="outputWordCounts" name="outputWordCounts" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">useDistribution</label>
                    <input class="form-control" type="checkbox" id="classifier_useDistribution" name="classifier_useDistribution" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">Ignore Accounts</label>
                    <textarea class="form-control" id="ignoreAccounts" name="ignoreAccounts" rows="7"></textarea>
                </div>


                <h3>NaiveBayes</h3>
                <div class="form-group">
                    <label class="col-sm-2">useKernelEstimator</label>
                    <input class="form-control" type="checkbox" id="bayes_useKernelEstimator" name="bayes_useKernelEstimator" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">useSupervisedDiscretization</label>
                    <input class="form-control" type="checkbox" id="bayes_useSupervisedDiscretization" name="bayes_useSupervisedDiscretization" />
                </div>

                <h3>J48</h3>
                <div class="form-group">
                    <label class="col-sm-2">binarySplits</label>
                    <input class="form-control" type="checkbox" id="j48_binarySplits" name="j48_binarySplits" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">confidenceFactor</label>
                    <input class="form-control" type="number" id="j48_confidenceFactor" name="j48_confidenceFactor" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">minNumObj</label>
                    <input class="form-control" type="number" id="j48_minNumObj" name="j48_minNumObj" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">numFolds</label>
                    <input class="form-control" type="number" id="j48_numFolds" name="j48_numFolds" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">errorPruning</label>
                    <input class="form-control" type="checkbox" id="j48_errorPruning" name="j48_errorPruning" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">saveInstanceData</label>
                    <input class="form-control" type="checkbox" id="j48_saveInstanceData" name="j48_saveInstanceData" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">newSeed</label>
                    <input class="form-control" type="number" id="j48_newSeed" name="j48_newSeed" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">unpruned</label>
                    <input class="form-control" type="checkbox" id="j48_unpruned" name="j48_unpruned" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">useLaplace</label>
                    <input class="form-control" type="checkbox" id="j48_useLaplace" name="j48_useLaplace" />
                </div>

                <h3>RandomForrest</h3>
                <div class="form-group">
                    <label class="col-sm-2">maxDepth</label>
                    <input class="form-control" type="number" id="rf_maxDepth" name="rf_maxDepth" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">newNumFeatures</label>
                    <input class="form-control" type="number" id="rf_newNumFeatures" name="rf_newNumFeatures" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">newNumTrees</label>
                    <input class="form-control" type="number" id="rf_newNumTrees" name="rf_newNumTrees" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">seed</label>
                    <input class="form-control" type="number" id="rf_seed" name="rf_seed" />
                </div>

                <h3>kNearest</h3>
                <div class="form-group">
                    <label class="col-sm-2">newCrossValidate</label>
                    <input class="form-control" type="checkbox" id="kN_newCrossValidate" name="kN_newCrossValidate" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">k</label>
                    <input class="form-control" type="number" id="kN_k" name="kN_k" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">newMeanSquared</label>
                    <input class="form-control" type="checkbox" id="kN_newMeanSquared" name="kN_newMeanSquared" />
                </div>
                <div class="form-group">
                    <label class="col-sm-2">newWindowSize</label>
                    <input class="form-control" type="number" id="kN_newWindowSize" name="kN_newWindowSize" />
                </div>
                <!--<div class="form-group">
                    <label class="col-sm-2">tfidfTransform</label>
                    <input type="checkbox" id="tfidfTransform" name="tfidfTransform" />
                </div>-->



                <button type="submit" class="btn btn-primary">Update</button>
            </form>
        </div>
    </div>
</div>


<script type="text/javascript" src="resource/js/bootstrap.min.js"></script>
<script type="text/javascript" src="resource/js/sentiment.js"></script>
</body>
</html>