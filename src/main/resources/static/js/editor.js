require.config({ paths: { vs: 'https://cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.45.0/min/vs' } });

require(['vs/editor/editor.main'], function () {
    const starterCode = document.getElementById('starterCode').value;

    window.editor = monaco.editor.create(document.getElementById('editor'), {
        value: starterCode,
        language: 'java',
        theme: 'vs-dark',
        automaticLayout: true
    });
});

async function sendCode(type) {
    const resultBox = document.getElementById('result');
    const problemId = document.getElementById('problemId').value;
    const code = window.editor.getValue();

    resultBox.removeAttribute('data-verdict');
    resultBox.textContent = 'Running...';

    try {
        const response = await fetch('/api/judge/' + type, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ problemId: Number(problemId), code: code })
        });

        const data = await response.json();

        resultBox.setAttribute('data-verdict', data.verdict);
        resultBox.textContent = data.verdict.replaceAll('_', ' ') + '\n\n' + data.message;

    } catch (err) {
        resultBox.textContent = 'Something went wrong: ' + err.message;
    }
}

document.getElementById('runBtn').addEventListener('click', function () {
    sendCode('run');
});

document.getElementById('submitBtn').addEventListener('click', function () {
    sendCode('submit');
});