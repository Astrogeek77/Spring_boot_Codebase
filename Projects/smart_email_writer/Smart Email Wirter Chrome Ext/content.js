console.log("Hello from Smart Email Assistant")

function getEmailContent(){
    const selectors = ['.h7', '.a3s.aiL', '.gmail_quote', '[role="presentation"]']
    for(const selector of selectors){
        const content = document.querySelector(selector);

        if(content) {
            content.innerText.trim();
        }
    }

    return "";
}

function findComposeToolbar(){
    const selectors = ['.btC', '.aDh', '[role="toolbar"]', '.gU.Up']
    for(const selector of selectors){
        const toolbar = document.querySelector(selector);

        if(toolbar) return toolbar;
    }

    return null;
}

function createAIButton(){
    const btn = document.createElement('div')
    injectButton.className = 'T-I J-J5-Ji aoO v7 T-I-atl L3'
    btn.style.marginRight = '8px'
    btn.style.borderRadius = '30%'
    btn.innerHTML = "AI Reply"
    btn.setAttribute('role', 'button');
    btn.setAttribute('data-tooltip', 'Generate AI reply')

    return btn;
}

function injectButton(){
    // get the button
    const existingButton = document.querySelector(".ai-reply-btn");
    if(existingButton) existingButton.remove();

    const toolbar = findComposeToolbar();
    if(!toolbar) {
        console.log("Error : toolbar not found")
        return
    }

    console.log("Toolbar found, creating AI button")

    const btn = createAIButton()
    btn.classList.add("ai-reply-btn")

    btn.addEventListener('click', async () => {
        try {
           btn.innerHTML = "Generating..."
           btn.disabled = true

           const emailContent = getEmailContent();
           const response = await fetch('http://localhost:8080/api/email/generate', {
            method: 'POST',
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({
                emailContent: emailContent,
                tone: "proffesional"
            })
           })

           if(!response.ok){
            throw new Error("Error: API Request Failed.")
           }

           const generatedReply = await response.text();
           const composeBox = document.querySelector('[role="textbox"][g_editable="true"]')

           if(composeBox){
            composeBox.focus();

            document.execCommand('insertText', false, generatedReply)
           } else {
            console.error("Error: Compose Box not found.")
           }
        } catch (error) {
            alert("Error: Failed to generate reply.")
            console.error("Error: " + error)
        } finally {
            btn.innerHTML = "AI Reply"
            btn.disabled = false
        }
    })

    toolbar.insertBefore(btn, toolbar.firstChild)
}

// mutation observer to oberve any composer element
const oberver = new MutationObserver((mutations) => {
    for(const mutation of mutations){
        // new nodes added to the DOM
        const addedNodes = Array.from(mutation.addedNodes)

        // Check the added notes to check a specific
        const hasComposeElements = addedNodes.some(node => 
            node.nodeType === node.ELEMENT_NODE 
            && (node.matches('.aDh, .btC, [role="dialog"]', ) 
                || node.querySelector('.aDh, .btC, [role="dialog"]'))
        );

        // if elemets found
        if(hasComposeElements) {
            console.log("Compose window detacted")
            setTimeout(injectButton, 500)
        }
    }
});

oberver.observe(document.body, {
    childList: true,
    subtree: true
})

// mutation observer to oberve URL changes