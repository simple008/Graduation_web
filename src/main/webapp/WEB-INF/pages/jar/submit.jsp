
<nav class="navbar navbar-default navbar-fixed-top navbar-main">
    <div id="fold-button" ng-click="showSidebar()" class="btn btn-default navbar-btn pull-left"><i class="fa fa-navicon"></i></div>
    <div class="navbar-title">Submit new Job</div>
</nav>
<div id="content-inner" ng-if="!noaccess &amp;&amp; jars &amp;&amp; !yarn">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Uploaded Jars</h3>
        </div>
        <div class="panel-body">
            <table class="table">
                <thead>
                <tr>
                    <th></th>
                    <th>Name</th>
                    <th>Upload Time</th>
                    <th>Entry Class</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="jar in jars track by jar.id">
                    <td><span class="icon-wrapper"><i ng-click="selectJar(jar.id)" ng-class="state.selected | getJarSelectClass:jar.id" class="show-pointer fa"></i></span></td>
                    <td>{{jar.name}}</td>
                    <td>{{jar.uploaded | amDateFormat:'YYYY-MM-DD, H:mm:ss'}}</td>
                    <td>
                        <div ng-repeat="entries in jar.entry"><span title="{{entries.description}}" ng-click="loadEntryClass(entries.name)" class="btn btn-default row-button">{{entries.name}}</span></div>
                    </td>
                    <td><span class="icon-wrapper"><i title="Delete" ng-click="deleteJar($event, jar.id)" class="show-pointer fa fa-remove"></i></span></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <table id="job-submit-table" ng-if="state.selected" class="table table-no-border">
        <tbody>
        <tr>
            <td class="td-large">
                <input type="text" placeholder="Entry Class" title="Entry Class" ng-model="state['entry-class']" class="form-control"/>
            </td>
            <td>
                <input type="text" placeholder="Parallelism" title="Parallelism" ng-model="state.parallelism" class="form-control"/>
            </td>
            <td><span id="fetch-plan" ng-click="getPlan()" class="btn btn-default">{{state['plan-button']}}</span>&nbsp;<i ng-if="state['plan-button'] == 'Getting Plan'" class="fa fa-spin fa-spinner"></i></td>
        </tr>
        <tr>
            <td colspan="2" class="td-large">
                <input type="text" placeholder="Program Arguments" title="Program Arguments" ng-model="state['program-args']" class="form-control"/>
            </td>
            <td><span id="job-submit" ng-click="runJob()" class="btn btn-success btn-sm">{{state['submit-button']}}</span>&nbsp;<i ng-if="state['submit-button'] == 'Submitting'" class="fa fa-spin fa-spinner"></i></td>
        </tr>
        </tbody>
    </table>
    <table ng-if="jid" class="table table-no-border">
        <tbody>
        <tr>
            <td class="text-center">Job was successfully submitted. To monitor,&nbsp;<a href="{{'#/jobs/' + jid}}">click here.</a></td>
        </tr>
        </tbody>
    </table>
    <table ng-if="error" class="table table-no-border">
        <tbody>
        <tr>
            <td>
                <pre>{{error}}</pre>
            </td>
        </tr>
        </tbody>
    </table>
    <div ng-if="plan" class="canvas-wrapper">
        <div job-plan="job-plan" plan="plan" jobid="{{plan.jid}}" set-node="changeNode(nodeid)" class="main-canvas"></div>
    </div>
    <table id="add-file-table" ng-if="!state.selected" class="table table-no-border">
        <tbody>
        <tr>
            <td id="add-file-button"><span ng-click="clearFiles()" class="btn btn-default btn-file">Add New&nbsp;<i class="fa fa-plus"></i>
            <input type="file" onchange="angular.element(this).scope().uploadFiles(this.files)"/></span></td>
            <td id="add-file-name" ng-if="uploader.file" title="{{uploader.file.name}}">{{uploader.file.name}}</td>
            <td id="add-file-status" ng-if="uploader.file"><span ng-if="uploader.error" class="error-area"><i>{{uploader.error}}</i></span><span ng-click="startUpload()" ng-if="uploader.upload" class="btn btn-success">&nbsp;Upload&nbsp;</span><span ng-if="uploader.success">{{uploader.success}}</span><span ng-if="uploader.progress" class="btn btn-progress-bar"><span ng-style="{width: uploader.progress + '%'}" class="btn btn-success btn-progress">{{uploader.progress}}%</span></span></td>
        </tr>
        </tbody>
    </table>
</div>