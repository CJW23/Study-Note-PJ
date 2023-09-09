const eventSource = new EventSource('http://localhost:9000/api/v1/sse/add', {headers: {}})

//메세지 수신
eventSource.onmessage = (event) => {
    console.log(event.data);
}

eventSource.onerror = (event) => {}