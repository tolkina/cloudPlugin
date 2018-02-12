AJS.$("#status-select").auiSelect2();

AP.request({
    url: '/rest/api/2/project',
    type: 'GET',
    success: function (projects) {
        projects = JSON.parse(projects);
        $(projects).each(function (index, value) {
            if (![[${projectKeys}]].includes(value.key)) {
                $("#status-select").append('<option value="' + value.key + '">' + value.key + '</option>');
            }
        });
    },
    error: function (xhr, statusText, errorThrown) {
        console.log(arguments);
    }
});

$("#change-config").click(function () {
    var keys = [];
    $("#status-select option:selected").each(function () {
        keys.push($(this).text());
    });

    $.ajax({
        url: "/save-keys",
        type: 'POST',
        headers: {
            "Authorization": "JWT " + [[${atlassianConnectToken}]]
        },
        data: {projectKeys: keys},
        success: function (response) {
            console.log(response)
        },
        error: function (xhr, statusText, errorThrown) {
            console.log(arguments);
        }
    });
});