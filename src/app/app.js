function handlePost(event) {
  
    const data = new FormData(event.target);
  
    const value = Object.fromEntries(data.entries());
  
    console.log({ value });

    fetch('http://localhost:8080/api/create', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(value)
    })
    .then(() => {
        handleGet(event);
    })
}

function handleGet(event) {
    const form = document.querySelector("form");
    const data = new FormData(form);
    let params = new URLSearchParams();

    data.forEach((value, key) => {
        if (value.trim() !== '') {
            params.append(key, value);
        }
    });
    
    params = params.toString();
    
    if (params) {
        params = '?' + params;
    }

    console.log(params)

    fetch(`http://localhost:8080/api/exercises${params.toString()}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        },
    })
    .then(response => response.json())
    .then(response => {
        
        const exercises = [];
        const volumes = [];
        
        response.forEach(item => {
          if ('sets' in item) {
            exercises.push(item);
          } else if ('totalSets' in item) {
            volumes.push(item);
          }
        });

        convertJSONToTable(exercises, 'exercises')
        convertJSONToTable(volumes, 'volume')
    })
}

function handleSubmit(event) {
    event.preventDefault();
    if (event.submitter.name === 'create') {
        handlePost(event)
    }
    else {
        handleGet(event)
    }
}

const form = document.querySelector("form");
form.addEventListener("submit", handleSubmit);

function handleDelete(event, row) {
    event.preventDefault();
    console.log('delete')
    obj=rowToJson(row);
    fetch('http://localhost:8080/api/delete', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
    })
    .then(() => {
        handleGet(event);
    })
    
}



function rowToJson(row) {
    const headers = row.closest("table").querySelectorAll("thead th");
    const cells = row.querySelectorAll("td");

    const json = {};
    headers.forEach((header, i) => {
        if (i < headers.length - 1) {
            const key = header.textContent.trim();
            const value = cells[i]?.textContent.trim();
            json[key] = value;
        }
    });

    console.log(json);

    return json;
}

function test(event) {
    event.preventDefault();
    console.log('test');
}

function convertJSONToTable(jsonData, id) {
    flag = false;
    if (Object.keys(jsonData).length === 0) {
        flag = true;
    }

    let table = '<table><thead><tr>';

    if (!flag) {
        headers = Object.keys(jsonData[0]);
    
        headers.forEach(header => table += `<th>${header}</th>`);
        if (id === 'exercises') { table += '<th></th>' }
        table += '</tr></thead><tbody>';

        jsonData.forEach(row => {
            table += '<tr>';
            headers.forEach(header => table += `<td>${row[header]}</td>`);

            if (id === 'exercises') {
                table += `<td><div id="delete" class="deleteButton">Delete</div></td>`;
            }
            table += '</tr>';
        });
        
        table += '</tbody></table>';
    }
    
    document.getElementById(id).innerHTML = table;

    if (id === 'exercises') {
        document.querySelectorAll(".deleteButton").forEach(button => {
            button.addEventListener("click", function(event) {
                handleDelete(event, button.closest("tr"));
            });
        });
    }
    
}